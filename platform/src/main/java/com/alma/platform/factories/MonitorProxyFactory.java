package com.alma.platform.factories;

import com.alma.platform.proxies.IMonitorProxy;
import com.alma.platform.proxies.SimpleMonitorProxy;

import java.lang.reflect.Proxy;

/**
 * Classe représentant une fabrique produisant des plugins enveloppés dans des proxies
 */
public class MonitorProxyFactory implements IFactory {
    @Override
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object instance = Class.forName(extension_name, true, loader).newInstance();

        Class<?>[] instanceInterfaces = instance.getClass().getInterfaces();
        Class<?>[] interfaces = new Class<?>[instanceInterfaces.length + 1];

        for(int i = 0; i < instanceInterfaces.length; i++) {
            interfaces[i] = instanceInterfaces[i];
        }
        interfaces[instanceInterfaces.length] = IMonitorProxy.class;
        return Proxy.newProxyInstance(loader, interfaces, new SimpleMonitorProxy(instance));
    }
}
