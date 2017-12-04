package com.easzz.corejava.pattern.proxy.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/12/1 14:34
 */
public class ClientController {
	//(1) 调用方法之前先验证是否有权限
	public static void main(String[] args) {
		ServerImpl server = new ServerImpl();
		Server serverProxy = (Server) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{Server.class}, new ValidateInvocationProxy(server));
		serverProxy.query("mike", "1");
		System.out.println(serverProxy.getClass().toString());
		System.out.println(serverProxy.getClass().getInterfaces()[0].getName());
		System.out.println(serverProxy.getClass().getSuperclass());
		Method[] methods = server.getClass().getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}

	}
}

interface Server {
	void query(String name, String pwd);
}

class ServerImpl implements Server {

	@Override
	public void query(String name, String pwd) {
		System.out.println("query success...");
	}
}

class ValidateInvocationProxy implements InvocationHandler {
	private Object server;

	public ValidateInvocationProxy(Object server) {
		this.server = server;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("代理类为 " + proxy.getClass().getName());
		method.invoke(server, args);
		//return method.invoke(server, args);
		return null;
		/*boolean validate = isValidate((String) args[0], (String) args[1]);
		if (validate) {
			System.out.println(new Date() + " " + args[0] + " 查询数据");
			System.out.println(proxy);
			return method.invoke(server, args);
		} else {
			System.out.println(new Date() + " " + args[0] + " 查询数据");
			System.out.println("账号密码错误，无权限查询..." + new Date());
			return null;
		}*/
	}

	private boolean isValidate(String name, String pwd) {
		return "mike".equals(name) && "1".equals(pwd);
	}
}