package com.alma.platform.factories;

/**
 * Classe représentant une fabrique classique de plugins
 */
public class ClassicFactory implements Factory{

    @Override
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(extension_name, true, loader).newInstance();
    }
}
