package com.alma.platform;

import com.alma.platform.backup.BackupManager;
import com.alma.platform.exceptions.NoSavedInstanceException;
import com.alma.platform.exceptions.PropertyNotFoundException;
import com.alma.platform.factories.ClassicFactory;
import com.alma.platform.factories.FailureSafeFactory;
import com.alma.platform.factories.IFactory;
import com.alma.platform.factories.MonitorProxyFactory;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;
import com.alma.platform.plugins.Plugin;
import com.alma.platform.proxies.IMonitorProxy;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Classe singleton représentant une plateforme de plugins
 */
public class Platform {

    private static Platform instance;
    private Properties config;
    private Monitor monitor;
    private BackupManager backupsManager;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private IFactory extensionFactory;

    private Platform() throws MalformedURLException, PropertyNotFoundException {
        extensionFactory = new ClassicFactory();
        backupsManager = new BackupManager();
        monitor = Monitor.getInstance();
        Parser parser = new Parser();

        try {
            plugins = parser.parseIt("src/main/resources/extensions.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on enregistre les plugins auprès du monitor
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            monitor.registerPlugin(plugin.getValue());
        }

        // on charge la config de la plateforme
        try {
            config = parser.loadConfig("src/main/resources/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // génération des urls du class loader
        int i = 0;
        File current_directory = new File(System.getProperty("user.dir"));
        String classpath_prefix = current_directory.getParentFile().toString() + "/";

        URL[] urls = new URL[plugins.size()];
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            urls[i] = new URL("file://" + classpath_prefix + plugin.getValue().getDirectory());
            i++;
        }
        classLoader = URLClassLoader.newInstance(urls);

        // en fonction du niveau de log, on utilise la factory appropriée
        int log_level = Integer.parseInt(config.getProperty("log-level"));
        switch (log_level) {
            case 0 :
                extensionFactory = new ClassicFactory();
                break;
            case 1 :
                extensionFactory = new MonitorProxyFactory();
                break;
            case 2 :
                extensionFactory = new FailureSafeFactory(backupsManager);
                break;
            default:
                extensionFactory = new ClassicFactory();
                break;
        }
    }

    /**
     * Méthode qui retourne l'instance de la plateforme
     * @return
     * @throws MalformedURLException
     * @throws PropertyNotFoundException
     */
    public static Platform getInstance() throws MalformedURLException, PropertyNotFoundException {
        if(instance == null) {
            synchronized (Platform.class) {
                if(instance == null) {
                    instance = new Platform();
                }
            }
        }
        return instance;
    }

    /**
     * Méthoide qui charge et retourne une extension précise
     * @param extension_name Le nom de l'extension que l'on veut récupérer
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object getExtension(String extension_name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSavedInstanceException {
        Plugin plugin = plugins.get(extension_name);
        Object objet = extensionFactory.get(plugin.getClassName(), classLoader);
        monitor.reportNewInstance(extension_name, plugin.getClassName() + "#" + System.identityHashCode(objet));
        if(extensionFactory instanceof MonitorProxyFactory) {
            ((IMonitorProxy) objet).setExtensionName(extension_name);
        }
        return objet;
    }

    /**
     * Méthode qui retourne une liste des noms des extensions à lancer au démarrage de l'application
     * @return
     */
    public List<String> getAutorunExtensions() {
        List<String> results = new ArrayList<>();
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            if(plugin.getValue().hasOption("autorun")) {
                if(plugin.getValue().getOption("autorun").equals("true")) {
                    results.add(plugin.getKey());
                }
            }
        }
        return results;
    }

    /**
     * Méthode qui retourne une liste des noms des extensions implémentant une interface donnée
     * @param interface_name
     * @return
     */
    public List<String> getByInterface(String interface_name) {
        List<String> results = new ArrayList<>();
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            if(plugin.getValue().getInterface().equals(interface_name)) {
                results.add(plugin.getKey());
            }
        }
        return results;
    }

    public static void main(String[] args) {
        try {
            // on instancie les extensions qui sont en mode autorun
            for(String plugin_name: Platform.getInstance().getAutorunExtensions()) {
                try {
                    Platform.getInstance().getExtension(plugin_name);
                } catch (Exception e) {
                    Monitor.getInstance().addLog(new Log(LogLevel.CRITICAL, e.getClass().getName(), e.getMessage()));
                }
            }
        } catch (MalformedURLException | PropertyNotFoundException e) {
            e.printStackTrace();
        }
    }
}
