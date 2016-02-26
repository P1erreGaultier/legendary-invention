package com.alma.platform;

import java.util.Properties;

/**
 * Classe représentant un plugin
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
