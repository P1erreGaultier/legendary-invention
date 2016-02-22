package com.alma.plateform;

/**
 * Created by E15A202A on 22/02/16.
 */
public interface Factory  {
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
