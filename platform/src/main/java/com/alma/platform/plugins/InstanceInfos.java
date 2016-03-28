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

    public void addMethodCall(String name) {
        int calls = 0;
        if(methodCalls.containsKey(name)) {
            calls = methodCalls.get(name);
        }
        methodCalls.put(name, calls + 1);
    }

    public int getMethodCall(String name) {
        return methodCalls.get(name);
    }

    public Set<String> getMethodsNames() {
        return methodCalls.keySet();
    }

    @Override
    public String toString() {
        return "[Instance name : " + name + " | methods calls : " + methodCalls.toString() + "]";
    }
}
