package com.alma.platform.exceptions;

/**
 * Exception lancée lorsqu'une sauvegarde d'instance n'a pas été trouvée
 */
public class NoSavedInstanceException extends Exception {

    public NoSavedInstanceException(String message) {
        super(message);
    }
}
