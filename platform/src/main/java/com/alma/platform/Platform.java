package com.alma.platform;

import com.alma.platform.factories.ClassicFactory;
import com.alma.platform.factories.Factory;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Properties;

/**
 * Classe singleton représentant une plateforme de plugins
 */
public class Platform {

    private static Platform instance;
    private URLClassLoader classLoader;
    private Map<String, Plugin> plugins;
    private Factory factory;

    private Platform() throws MalformedURLException {
        factory = new ClassicFactory();
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
            urls[i] = new URL("file://" + classpath_prefix + plugin.getValue().getProperties().getProperty("directory"));
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
        return factory.get(plugin.getProperties().getProperty("class"), classLoader);
    }

    public static void main(String[] args) {
        try {
            JavaBean jb = (JavaBean) Platform.getInstance().getExtension("JavaBean");
            System.out.println(jb);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
    }
}
