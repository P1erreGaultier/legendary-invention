package com.alma.platform.monitor;

/**
 * Interface représentant un observateur d'évènements liés au Moniteur d'activités
 */
public interface LogObserver {

    /**
     * Méthode exécutée à chaque nouvel ajout de log par le moniteur
     * @param log
     */
    void execute(String log);
}
