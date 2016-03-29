package com.alma.platform.exceptions;

/**
 * Exception lancée lorsqu'une propriété n'a pas été trouvée
 */
public class PropertyNotFoundException extends Exception {

    public PropertyNotFoundException(String msg) {
        super(msg);
    }
}
