package com.alma.platform.monitor;

/**
 * Classe représentant un observateur d'évènements liées à l'instanciation de nouvelles instances
 */
public interface NewInstanceObserver {

    /**
     * Méthode exécutée à chaque instanciation d'une nouvelle instance
     * @param extension_name
     * @param instance_name
     */
    void execute(String extension_name, String instance_name);
}
