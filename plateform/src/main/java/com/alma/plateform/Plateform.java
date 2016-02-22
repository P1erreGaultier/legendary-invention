package com.alma.plateform;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by E122371M on 11/02/16.
 */
public class Plateform {

    private static Plateform instance;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;

    private Plateform() throws MalformedURLException {
        plugins = new HashMap<>();
        //TODO chargement de toutes les extensions depuis le fichier de description
        Plugin javaBean = new Plugin();
        Properties props = new Properties();
        props.setProperty("class", "com.alma.plateform.JavaBean");
        javaBean.setProperties(props);
        plugins.put("JavaBean", javaBean);
        // TODo chargement de tous les classPath depuis le fichier de description
        URL[] urls = new URL[1];
        urls[0] = new URL("file:///comptes/E15A202A/legendary-invention/plateform/target/classes/com/alma/plateform/");
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
