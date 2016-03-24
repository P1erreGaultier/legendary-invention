package com.alma.platform.proxies;

import com.alma.platform.Platform;
import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Classe représentant un proxy qui gère des objets que l'on a échoué à instancier
 */
public class FailureProxy implements InvocationHandler {

    private Object target;
    private String extension_name;
    private boolean isRepaired;

    public FailureProxy(Object target, String extension_name) {
        this.target = target;
        this.extension_name = extension_name;
        isRepaired = false;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(! isRepaired) {
            try {
                Object new_target = Platform.getInstance().getExtension(extension_name);
                target = new_target;
                isRepaired = true;
            } catch (Exception e) {
                Monitor.getInstance().addLog(new Log(LogLevel.WARNING, extension_name, e.getMessage()));
            }
        }

        return method.invoke(target, objects);
    }
}
