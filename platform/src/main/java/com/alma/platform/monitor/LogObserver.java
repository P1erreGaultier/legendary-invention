package com.alma.platform.monitor;

/**
 * Interface représentant un observateur d'évènements liés au Moniteur d'activités
 */
public interface LogObserver {
    void execute(String log);
}
