package com.alma.plateform;

import java.util.Properties;

/**
 * Created by E122371M on 11/02/16.
 */
public class Plugin {

    private Properties properties;

    public Plugin() {
        properties = new Properties();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
