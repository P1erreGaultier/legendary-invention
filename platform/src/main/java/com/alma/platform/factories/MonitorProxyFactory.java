package com.alma.platform.factories;

import com.alma.platform.proxies.MonitorProxy;

import java.lang.reflect.Proxy;

/**
 * Classe représentant une fabrique produisant des plugins enveloppés dans des proxies
 */
public class MonitorProxyFactory implements IFactory {
    @Override
    public Object get(String extension_name, ClassLoader loader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object instance = Class.forName(extension_name, true, loader).newInstance();
        Class<?>[] interfaces = instance.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, new MonitorProxy(instance));
    }
}
