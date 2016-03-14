package com.alma.platform.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe singleton représentant un moniteur d'activités
 */
public class Monitor {

    private static Monitor instance;
    private List<LogObserver> logObservers;
    private List<String> logs;

    private Monitor() {
        logObservers = new ArrayList<>();
        logs = new ArrayList<>();
    }

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

    public void addLog(String log) {
        logs.add(log);
        triggerLogObservers(log);
    }

    public void addLogListener(LogObserver observer) {
        logObservers.add(observer);
    }

    public static void main(String[] args) {
        Monitor.getInstance().addLogListener(new LogObserver() {
            @Override
            public void execute(String log) {
                System.out.println("log : " + log);
            }
        });

        Monitor.getInstance().addLog("toto");
    }
}
