package com.alma.platform.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E122371M on 14/03/16.
 */
public class Monitor {

    private static Monitor instance;
    private List<MonitorObserver> logObservers;
    private List<>

    private Monitor() {
        logObservers = new ArrayList<>();
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

    private void triggerLogObservers() {
        for(MonitorObserver observer : logObservers) {
            observer.execute();
        }
    }

    public void addLog(String log) {

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
    }
}
