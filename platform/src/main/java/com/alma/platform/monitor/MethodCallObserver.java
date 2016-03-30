package com.alma.platform.monitor;

/**
 * Classe représentant un observateur liées à lappel d'une méthode
 */
public interface MethodCallObserver {

    /**
     * Méthode exécutée à chaque nouvel appel d'une méthode
     * @param extension_name
     * @param instance_name
     * @param method_name
     * @param nb_calls
     */
    void execute(String extension_name, String instance_name, String method_name, int nb_calls);
}
