package com.alma.platform.factories;

import com.alma.platform.exceptions.NoSavedInstanceException;

/**
 * Interface représentant une fabrique de plugins
 */
public interface IFactory {
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSavedInstanceException;
}
