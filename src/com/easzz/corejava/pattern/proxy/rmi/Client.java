package com.easzz.corejava.pattern.proxy.rmi;

import java.lang.reflect.InvocationHandler;

/**
 * Created by easzz on 2017/12/2 10:27
 */
public class Client {
	public static void main(String[] args) {
		InvocationHandler invocationHandler = new ServerInvokeHandler(RemoteServer.class, "127.0.0.1", 8001);
		Server s= (Server) RemoteServiceProxyFactory.getRemoteServerProxy(invocationHandler);
		//Server remoteServerProxy = (Server) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Server.class}, invocationHandler);
		try {
			//String result = remoteServerProxy.getServer("mike");
			String result = s.getServer("mike");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
