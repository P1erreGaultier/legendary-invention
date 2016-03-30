package com.alma.platform.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe représentant des informations à propos d'une instance d'un objet
 */
public class InstanceInfos {

    private String name;
    private Map<String, Integer> methodCalls;

    public InstanceInfos(String name) {
        this.name = name;
        methodCalls = new HashMap<>();
    }

    /**
     * Méthode qui ajoute un appel de méthode pour une instance donnée
     * @param method_name
     */
    public void addMethodCall(String method_name) {
        int calls = 0;
        if(methodCalls.containsKey(method_name)) {
            calls = methodCalls.get(method_name);
        }
        methodCalls.put(method_name, calls + 1);
    }

    /**
     * Accesseur sur le nombre d'appels d'une méthode
     * @param method_name
     * @return
     */
    public int getMethodCall(String method_name) {
        return methodCalls.get(method_name);
    }

    /**
     * ACcesseur sur les noms des méthodes qui ont été appelées
     * @return
     */
    public Set<String> getMethodsNames() {
        return methodCalls.keySet();
    }

    /**
     * Méthode qui renvoire l'objet sous forme de chaîne de caractères
     * @return
     */
    @Override
    public String toString() {
        return "[Instance name : " + name + " | methods calls : " + methodCalls.toString() + "]";
    }
}
