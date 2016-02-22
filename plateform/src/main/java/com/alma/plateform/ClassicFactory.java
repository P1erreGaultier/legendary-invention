package com.alma.plateform;

/**
 * Created by E15A202A on 22/02/16.
 */
public class ClassicFactory implements Factory{

    @Override
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(extension_name, true, loader).newInstance();
    }
}
