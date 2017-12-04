package com.easzz.corejava.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/8/28 14:58
 */
public class DynamicDemo {
	public static void main(String[] args) {
		RealSubject realSubject = new RealSubject();
		ProxyHandler proxyHandler = new ProxyHandler(realSubject);
		Subject o = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), proxyHandler);
		o.request();
	}
}

interface Subject {
	void request();
}

class RealSubject implements Subject {

	@Override
	public void request() {
		System.out.println("RealSubject...");
	}
}

class ProxyHandler implements InvocationHandler {
	private Subject subject;

	public ProxyHandler(Subject subject) {
		this.subject = subject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("do something before....");
		Object invoke = method.invoke(subject, args);
		System.out.println("do something after...");
		return invoke;
	}
}