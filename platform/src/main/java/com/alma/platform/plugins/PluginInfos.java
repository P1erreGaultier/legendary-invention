package com.alma.platform.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant des informations liées à un plugin
 */
public class PluginInfos {

    private String name;
    private Map<String, InstanceInfos> instances;

    public PluginInfos(String n) {
        name = n;
        instances = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getNbInstances() {
        return instances.size();
    }

    /**
     * Méthode qui ajoute une instance
     * @param instance_name
     */
    public void addInstances(String instance_name) {
        InstanceInfos infos = new InstanceInfos(instance_name);
        instances.put(instance_name, infos);
    }

    /**
     * Méthode qui ajoute l'appel d'une méthode pour une instance donnée
     * @param instance_name
     * @param method_name
     */
    public void addMethodCall(String instance_name, String method_name) {
        if(instances.containsKey(instance_name)) {
            instances.get(instance_name).addMethodCall(method_name);
        } else {
            addInstances(instance_name);
            instances.get(instance_name).addMethodCall(method_name);
        }
    }

    /**
     * Accesseur sur les appels des méthodes pour une instance donnée
     * @param instance_name
     * @return
     */
    public Map<String, Integer> getMethodsCalls(String instance_name) {
        Map<String, Integer> calls = new HashMap<>();
        if(instances.containsKey(instance_name)) {
            InstanceInfos instance_infos = instances.get(instance_name);
            for(String method_name : instance_infos.getMethodsNames()) {
                calls.put(method_name, instance_infos.getMethodCall(method_name));
            }
        }
        return calls;
    }

    @Override
    public String toString() {
        return "[ Plugin : " + name + " | instances : " + instances.toString() + " ]";
    }
}
