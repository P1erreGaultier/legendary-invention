package com.alma.platform.factories;

import com.alma.platform.backup.BackupManager;
import com.alma.platform.exceptions.NoSavedInstanceException;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;
import com.alma.platform.proxies.FailureProxy;
import com.alma.platform.proxies.IMonitorProxy;
import com.alma.platform.proxies.SimpleMonitorProxy;

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
    public Object get(String extension_name, ClassLoader loader) throws NoSavedInstanceException, ClassNotFoundException {
        Object instance;
        Class<?> classe = Class.forName(extension_name, true, loader);
        Class<?>[] instanceInterfaces = classe.getInterfaces();
        Class<?>[] interfaces = new Class<?>[instanceInterfaces.length + 1];

        System.arraycopy(instanceInterfaces, 0, interfaces, 0, instanceInterfaces.length);
        interfaces[instanceInterfaces.length] = IMonitorProxy.class;
        try {
            instance = Class.forName(extension_name, true, loader).newInstance();
            backupsManager.commit(extension_name, instance);

            return Proxy.newProxyInstance(loader, interfaces, new SimpleMonitorProxy(instance));
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Monitor.getInstance().addLog(new Log(LogLevel.CRITICAL, extension_name, e.getMessage()));
            instance = backupsManager.pull(extension_name).getInstance();

            return Proxy.newProxyInstance(loader, interfaces, new FailureProxy(instance, extension_name));
        }
    }
}
