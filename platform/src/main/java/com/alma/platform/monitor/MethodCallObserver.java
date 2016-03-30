package com.alma.platform.monitor;

/**
 * Classe représentant un observateur liées à lappel d'une méthode
 */
public interface MethodCallObserver {

    void execute(String extension_name, String instance_name, String method_name, int nb_calls);
}
