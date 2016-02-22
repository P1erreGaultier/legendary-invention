package com.alma.plateform;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Created by E122371M on 11/02/16.
 */
public class Plateform {

    private static Plateform instance;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;

    private Plateform() throws MalformedURLException {
        Parser p = new Parser();
        try {
            plugins = p.parseIt("/comptes/E122371M/legendary-invention/plateform/src/main/resources/extensions.txt");
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

        return Class.forName(plugin.getProperties().getProperty("class"), true, classLoader).newInstance();
    }

    public static void main(String[] args) {
        try {
            JavaBean bean = (JavaBean) Plateform.getInstance().getExtension("JavaBean");
            bean.sayToto();
        } catch (ClassNotFoundException | IllegalAccessException | MalformedURLException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
