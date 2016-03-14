package com.alma.platform.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe singleton représentant un moniteur d'activités
 */
public class Monitor {

    private static Monitor instance;
    private List<MonitorObserver> logObservers;
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
     * Notifie tous les observeurs
     */
    private void triggerLogObservers(String log) {
        for(MonitorObserver observer : logObservers) {
            observer.execute();
        }
    }

    public void addLog(String log) {
        logs.add(log);
        //triggerLogObservers();
    }

    public void addLogListener(MonitorObserver observer) {
        logObservers.add(observer);
    }

    public static void main(String[] args) {
        Monitor.getInstance().addLogListener(new MonitorObserver() {
            @Override
            public void execute() {
                System.out.println("moniteur ok");
            }
        });

        Monitor.getInstance().addLog("toto");
    }
}
