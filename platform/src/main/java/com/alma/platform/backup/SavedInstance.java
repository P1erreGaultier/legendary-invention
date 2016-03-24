package com.alma.platform.backup;

import java.util.Date;

/**
 * Classe représentant une instance sauvegardée à une date donnée
 */
public class SavedInstance {

    private Date timestamp;
    private Object instance;

    public SavedInstance(Date timestamp, Object instance) {
        this.timestamp = timestamp;
        this.instance = instance;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Object getInstance() {
        return instance;
    }
}
