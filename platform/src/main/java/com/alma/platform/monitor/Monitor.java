package com.alma.platform.monitor;

import com.alma.platform.plugins.Plugin;
import com.alma.platform.plugins.PluginInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe singleton représentant un moniteur de l'activités des plugins
 */
public class Monitor {

    private static Monitor instance;
    private Map<String, PluginInfos> extensions;
    private List<LogObserver> logObservers;
    private List<NewInstanceObserver> newInstanceObservers;
    private List<MethodCallObserver> methodCallObservers;
    private List<Log> logs;

    private Monitor() {
        logObservers = new ArrayList<>();
        newInstanceObservers = new ArrayList<>();
        methodCallObservers = new ArrayList<>();
        extensions = new HashMap<>();
        logs = new ArrayList<>();
    }

    /**
     * Méthode qui retourne l'instance du Moniteur
     * @return
     */
    public static Monitor getInstance() {
        if(instance == null) {
            synchronized (Monitor.class) {
                if(instance == null) {
                    instance = new Monitor();
                }
            }
        }
        return instance;
    }

    /**
     * Méthode qui enregistre un plugin à surveiller
     */
    public void registerPlugin(Plugin plugin) {
        extensions.put(plugin.getName(), new PluginInfos(plugin.getName()));
    }

    /**
     * Méthode qui retourne les infos sur un plugin surveillé par le moniteur
     * @param name
     * @return
     */
    public PluginInfos getPluginInfos(String name) {
        PluginInfos infos = null;
        if(extensions.containsKey(name)) {
            infos = extensions.get(name);
        }
        return infos;
    }

    /**
     * Méthode qui signale une nouvelle instanciation d'une extension
     * @param extension_name
     * @param instance_name
     */
    public void reportNewInstance(String extension_name, String instance_name) {
        if(extensions.containsKey(extension_name)) {
            PluginInfos infos = extensions.get(extension_name);
            infos.addInstances(instance_name);
            triggerNewInstanceObservers(extension_name, instance_name);
        }
    }

    /**
     * Méthode qui signale l'appel d'une nouvelle méthode
     * @param extension_name
     * @param instance_name
     * @param method_name
     */
    public void reportMethodCall(String extension_name, String instance_name, String method_name) {
        if(extensions.containsKey(extension_name)) {
            PluginInfos infos = extensions.get(extension_name);
            infos.addMethodCall(instance_name, method_name);
            triggerMethodCallObservers(extension_name, instance_name, method_name);
        }
    }

    /**
     * Méthode qui ajoute un log
     */
    public void addLog(Log log) {
        logs.add(log);
        triggerLogObservers(log);
    }

    /**
     * Méthode qui ajoute un observateur de log
     * @param observer
     */
    public void addLogListener(LogObserver observer) {
        logObservers.add(observer);
    }

    /**
     * Méthode qui ajoute un observateur de nouvelle instance
     * @param observer
     */
    public void addNewInstanceListener(NewInstanceObserver observer) {
        newInstanceObservers.add(observer);
    }

    /**
     * Méthode qui ajoute un observateur d'appel de méthodes
     * @param observer
     */
    public void addMethodCallListener(MethodCallObserver observer) {
        methodCallObservers.add(observer);
    }

    /**
     * Notifie tous les observeurs de l'ajout d'un log
     */
    private void triggerLogObservers(Log log) {
        for(LogObserver observer : logObservers) {
            observer.execute(log);
        }
    }

    /**
     * Notifie tous les observateurs de l'instanciation d'une nouvelle méthode
     * @param instance_name
     */
    private void triggerNewInstanceObservers(String extension_name, String instance_name) {
        for(NewInstanceObserver observer : newInstanceObservers) {
            observer.execute(extension_name, instance_name);
        }
    }

    /**
     * Notifie tous les observateurs de l'appel d'une méthode pour une instance donnée
     * @param extension_name
     * @param instance_name
     * @param method_name
     */
    private void triggerMethodCallObservers(String extension_name, String instance_name, String method_name) {
        if(extensions.containsKey(extension_name)) {
            PluginInfos infos = extensions.get(extension_name);
            Map<String, Integer> methodCalls = infos.getMethodsCalls(instance_name);
            if(methodCalls.size() > 0) {
                int nb_calls = methodCalls.get(method_name);
                for(MethodCallObserver observer : methodCallObservers) {
                    observer.execute(extension_name,instance_name, method_name, nb_calls);
                }
            }
        }
    }
}
