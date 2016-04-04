package com.alma.platform.proxies;

import com.alma.platform.monitor.Log;
import com.alma.platform.monitor.LogLevel;
import com.alma.platform.monitor.Monitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Classe représentant un proxy de monitoring de plugin
 */
public class SimpleMonitorProxy implements InvocationHandler {

    protected Object target;
    protected String extensionName;

    public SimpleMonitorProxy(Object o) {
        target = o;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("getExtensionName")) {
            return extensionName;
        } else if ((method.getName().equals("setExtensionName")) && (args[0] instanceof String)) {
            extensionName = (String) args[0];
            return null;
        } else {
            String msg = "Method " + method.getName() + " called by " + o.getClass().getName() + "#" + System.identityHashCode(o);
            Log log = new Log(LogLevel.NORMAL, target.getClass().getName(), msg);
            Monitor.getInstance().addLog(log);
            Monitor.getInstance().reportMethodCall(extensionName, o.getClass().getName() + "#" + System.identityHashCode(o), method.getName());

            return method.invoke(target, args);
        }
    }
}
