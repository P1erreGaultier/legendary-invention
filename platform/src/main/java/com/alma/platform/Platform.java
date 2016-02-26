package com.alma.platform;

import com.alma.platform.factories.ClassicFactory;
import com.alma.platform.factories.Factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Classe singleton représentant une plateforme de plugins
 */
public class Platform {

    private static Platform instance;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private Factory facto;

    private Platform() throws MalformedURLException {
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

    public static Platform getInstance() throws MalformedURLException {
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
        return facto.get(plugin.getProperties().getProperty("class"), classLoader);
    }

    public static void main(String[] args) {
        try {
            JavaBean jb = (JavaBean) Platform.getInstance().getExtension("JavaBean");
            System.out.println(jb);
        } catch (ClassNotFoundException | IllegalAccessException | MalformedURLException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
