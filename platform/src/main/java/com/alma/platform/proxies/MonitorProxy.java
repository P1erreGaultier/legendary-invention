package com.alma.platform.proxies;

import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
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
        String msg = "Method " + method.getName() + " called by " + o.getClass().getName();

        Log log = new Log(LogLevel.NORMAL, target.getClass().getName(), msg);
        Monitor.getInstance().addLog(log);

        System.out.println("Console log : " + log);

        return method.invoke(target, args);
    }
}
