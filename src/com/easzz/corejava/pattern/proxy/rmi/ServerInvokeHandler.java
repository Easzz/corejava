package com.easzz.corejava.pattern.proxy.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by easzz on 2017/12/2 9:41
 */
public class ServerInvokeHandler implements InvocationHandler {
	private Class<?> classType;
	private String host;
	private Integer port;

	public Class<?> getClassType() {
		return classType;
	}

	public ServerInvokeHandler(Class<?> aClass, String host, Integer port) {
		this.classType = aClass;
		this.host = host;
		this.port = port;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//客户端建立连接，发送请求信息
		//封装请求信息
		Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), args);
		Connector connector = new Connector();
		connector.connect(host, port);
		System.out.println("before send call: " + call);
		connector.sendCall(call);
		Call receive = connector.receive();
		System.out.println(receive);
		connector.close();
		return receive.getResult();
	}
}
