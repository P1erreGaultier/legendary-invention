package com.alma.platform.backup;

import com.alma.platform.exceptions.NoSavedInstanceException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant un gestionnaire de sauvegardes d'instances
 */
public class BackupManager {

    private Map<String, SavedInstance> backups;

    public BackupManager() {
        backups = new HashMap<>();
    }

    public void commit(String extension_name, Object instance) {
        SavedInstance backup = new SavedInstance(new Date(), instance);
        backups.put(extension_name, backup);
    }

    public SavedInstance pull(String extension_name) throws NoSavedInstanceException {
        if(! backups.containsKey(extension_name)) {
            throw new NoSavedInstanceException("Saved instance for " + extension_name + " cannot be found");
        }
        return backups.get(extension_name);
    }
}
