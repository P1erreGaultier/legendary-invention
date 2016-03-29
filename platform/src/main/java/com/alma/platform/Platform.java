package com.alma.platform;

import com.alma.platform.backup.BackupManager;
import com.alma.platform.exceptions.NoSavedInstanceException;
import com.alma.platform.exceptions.PropertyNotFoundException;
import com.alma.platform.factories.ClassicFactory;
import com.alma.platform.factories.FailureSafeFactory;
import com.alma.platform.factories.IFactory;
import com.alma.platform.factories.MonitorProxyFactory;
import com.alma.platform.monitor.Monitor;
import com.alma.platform.plugins.Plugin;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe singleton représentant une plateforme de plugins
 */
public class Platform {

    private static Platform instance;
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

        // TODO check si les variables de config obligatoires sont présentes

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
        monitor.reportNewInstance(plugin.getName());
        return extensionFactory.get(plugin.getClassName(), classLoader);
    }

    /**
     * Méthode qui active/désactive le monitoring complet des prochaines extensions à être instanciées
     */
    public void switchMonitoring() {
        if( ! (extensionFactory instanceof MonitorProxyFactory)) {
            extensionFactory = new MonitorProxyFactory();
        } else {
            extensionFactory = new ClassicFactory();
        }
    }

    /**
     * Méthode qui active/désactive le mode Failure Safe de la plateforme
     */
    public void switchFailureSafeMode() {
        if(! (extensionFactory instanceof FailureSafeFactory)) {
            extensionFactory = new FailureSafeFactory(backupsManager);
        } else {
            extensionFactory = new ClassicFactory();
        }
    }

    /**
     * Méthode qui retounr eune liste des noms des extensions à lancer au démarrage de l'application
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
     *
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
            //Platform.getInstance().switchMonitoring();
            Platform.getInstance().switchFailureSafeMode();
            for(String plugin_name: Platform.getInstance().getAutorunExtensions()) {
                Platform.getInstance().getExtension(plugin_name);
            }
        } catch (MalformedURLException | IllegalAccessException | InstantiationException | ClassNotFoundException | PropertyNotFoundException | NoSavedInstanceException e) {
            e.printStackTrace();
        }
    }
}
