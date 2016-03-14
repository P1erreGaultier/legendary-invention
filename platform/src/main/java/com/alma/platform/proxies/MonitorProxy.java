package com.alma.platform.proxies;

import com.alma.platform.monitor.Monitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Classe représentant un proxy de monitoring de plugin
 */
public class MonitorProxy implements InvocationHandler {

    private Object target;

    public MonitorProxy(Object o) {
        target = o;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        String log = "Method " + method.getName() + " called by " + o.getClass().getName();
        Monitor.getInstance().addLog(log);

        System.out.println("Console log : " + log);

        return method.invoke(target, args);
    }
}
