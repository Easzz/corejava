package com.easzz.corejava.pattern.proxy.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/12/2 9:56
 */
public class RemoteServiceProxyFactory {
	public static Object getRemoteServerProxy(InvocationHandler handler) {
		Class<?> classType = ((ServerInvokeHandler) handler).getClassType().getInterfaces()[0];
		return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, handler);

	}
}
