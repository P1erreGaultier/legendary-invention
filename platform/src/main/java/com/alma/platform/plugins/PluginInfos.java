package com.alma.platform.plugins;

/**
 * Classe représentant des informations liées à un plugin
 */
public class PluginInfos {

    private String name;
    private int nb_instances;

    public PluginInfos(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public int getNb_instances() {
        return nb_instances;
    }

    public void addInstances() {
        nb_instances++;
    }

    @Override
    public String toString() {
        return "[ Plugin " + name + " | nb_instances : " + nb_instances + " ]";
    }
}
