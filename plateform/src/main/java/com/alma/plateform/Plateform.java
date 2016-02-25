package com.alma.plateform;

import com.alma.plateform.factories.ClassicFactory;
import com.alma.plateform.factories.Factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Classe singleton représentant une plateforme de plugins
 */
public class Plateform {

    private static Plateform instance;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private Factory facto;

    private Plateform() throws MalformedURLException {
        facto = new ClassicFactory();
        Parser p = new Parser();
        try {
            plugins = p.parseIt("src/main/resources/extensions.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // génération des urls du class loader
        int i = 0;
        URL[] urls = new URL[plugins.size()];
        for(Map.Entry<String, Plugin> plugin : plugins.entrySet()) {
            urls[i] = new URL("file://" + plugin.getValue().getProperties().getProperty("classpath"));
            i++;
        }
        classLoader = URLClassLoader.newInstance(urls);
    }

    public static Plateform getInstance() throws MalformedURLException {
        if(instance == null) {
            synchronized (Plateform.class) {
                if(instance == null) {
                    instance = new Plateform();
                }
            }
        }
        return instance;
    }

    public Object getExtension(String extension_name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Plugin plugin = plugins.get(extension_name);
        return facto.get(plugin.getProperties().getProperty("class"), classLoader);
    }
}
