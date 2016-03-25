package com.alma.platform.backup;

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

    public SavedInstance pull(String extension_name) {
        return backups.get(extension_name);
    }
}