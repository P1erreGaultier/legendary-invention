package com.alma.platform.factories;

import com.alma.platform.exceptions.NoSavedInstanceException;

/**
 * Interface représentant une fabrique de plugins
 */
public interface IFactory {

    /**
     * Méthode qui instancie l'objet principal d'une extension et la renvoie
     * @param extension_name
     * @param loader
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSavedInstanceException
     */
    Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSavedInstanceException;
}
