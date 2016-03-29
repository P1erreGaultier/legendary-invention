package com.alma.platform.factories;

import com.alma.platform.backup.BackupManager;
import com.alma.platform.exceptions.NoSavedInstanceException;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;
import com.alma.platform.proxies.FailureProxy;

import java.lang.reflect.Proxy;

/**
 * Classe représentant une factory produisant des objets protégés contre les crashs
 */
public class FailureSafeFactory implements IFactory {

    private BackupManager backupsManager;

    public FailureSafeFactory(BackupManager backupsManager) {
        this.backupsManager = backupsManager;
    }

    @Override
    public Object get(String extension_name, ClassLoader loader) throws NoSavedInstanceException {
        Object instance;
        try {
            instance = Class.forName(extension_name, true, loader).newInstance();
            backupsManager.commit(extension_name, instance);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Monitor.getInstance().addLog(new Log(LogLevel.CRITICAL, extension_name, e.getMessage()));
            Object savedInstance = backupsManager.pull(extension_name).getInstance();
            Class<?>[] interfaces = savedInstance.getClass().getInterfaces();
            instance = Proxy.newProxyInstance(loader, interfaces, new FailureProxy(savedInstance, extension_name));
        }
        return instance;
    }
}
