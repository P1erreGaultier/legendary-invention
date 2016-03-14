package com.alma.platform.monitor;

import com.alma.platform.plugins.Plugin;
import com.alma.platform.plugins.PluginInfos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe singleton représentant un moniteur de l'activités des plugins
 */
public class Monitor {

    private static Monitor instance;
    private Map<String, PluginInfos> extensions;
    private List<LogObserver> logObservers;
    private List<String> logs;

    private Monitor() {
        logObservers = new ArrayList<>();
        extensions = new ConcurrentHashMap<>();
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
     * Notifie tous les observeurs de l'ajout d'un log
     */
    private void triggerLogObservers(String log) {
        for(LogObserver observer : logObservers) {
            observer.execute(log);
        }
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
     * @param name
     */
    public void reportNewInstance(String name) {
        if(extensions.containsKey(name)) {
            PluginInfos infos = extensions.get(name);
            infos.addInstances();
        }
    }

    /**
     * Méthode qui ajoute un log
     */
    public void addLog(String log) {
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
}
