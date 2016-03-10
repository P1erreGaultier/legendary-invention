package com.alma.platform;

import com.alma.platform.exceptions.PropertyNotFound;
import com.alma.platform.factories.ClassicFactory;
import com.alma.platform.factories.IFactory;
import com.alma.platform.factories.MonitorProxyFactory;

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
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private IFactory extensionFactory;

    private Platform() throws MalformedURLException, PropertyNotFound {
        extensionFactory = new ClassicFactory();
        Parser parser = new Parser();

        try {
            plugins = parser.parseIt("src/main/resources/extensions.txt");
        } catch (IOException e) {
            e.printStackTrace();
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

    public static Platform getInstance() throws MalformedURLException, PropertyNotFound {
        if(instance == null) {
            synchronized (Platform.class) {
                if(instance == null) {
                    instance = new Platform();
                }
            }
        }
        return instance;
    }

    public Object getExtension(String extension_name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Plugin plugin = plugins.get(extension_name);
        return extensionFactory.get(plugin.getClassName(), classLoader);
    }

    public void monitoringOn() {
        extensionFactory = new MonitorProxyFactory();
    }

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
            //Platform.getInstance().monitoringOn();
            for(String plugin_name: Platform.getInstance().getAutorunExtensions()) {
                Platform.getInstance().getExtension(plugin_name);
            }
        } catch (MalformedURLException | IllegalAccessException | InstantiationException | ClassNotFoundException | PropertyNotFound e) {
            e.printStackTrace();
        }
    }
}
