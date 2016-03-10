package com.alma.platform.factories;

/**
 * Interface représentant une fabrique de plugins
 */
public interface IFactory {
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
