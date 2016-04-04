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
public class FailureProxy extends SimpleMonitorProxy implements InvocationHandler {

    private Object target;
    private String className;
    private boolean isRepaired;

    public FailureProxy(Object target, String className) {
        super(target);
        this.target = target;
        this.className = className;
        isRepaired = false;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(! isRepaired) {
            try {
                target = Platform.getInstance().getExtension(className);
                isRepaired = true;
            } catch (Exception e) {
                Monitor.getInstance().addLog(new Log(LogLevel.WARNING, className, e.getMessage()));
            }
        }

        return super.invoke(o, method, objects);
    }
}
