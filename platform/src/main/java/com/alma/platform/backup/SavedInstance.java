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

    /**
     * Accesseur sur le timestamp de la sauvegarde
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Accesseur sur l'instance sauvegardée
     * @return
     */
    public Object getInstance() {
        return instance;
    }
}
