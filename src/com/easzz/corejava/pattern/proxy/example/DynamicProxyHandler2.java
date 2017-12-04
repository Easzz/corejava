package com.easzz.corejava.pattern.proxy.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/12/1 15:52
 */
class DynamicProxyHandler2 implements InvocationHandler {
	private Object proxied;

	public DynamicProxyHandler2(Object proxied) {
		this.proxied = proxied;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy: " + proxy);
		return method.invoke(proxied, args);
	}
}

class E23_SimpleDynamicProxyDemo2 {
	public static void consumer(Interface iface) {
		iface.doSomething();
		iface.somethingElse("bonobo");
	}

	public static void main(String[] args) {
		Interface real = new RealObject();
		//consumer(real);
		Interface proxy = (Interface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{Interface.class},
				new DynamicProxyHandler2(real));
		consumer(proxy);
	}
}

interface Interface {
	void doSomething();

	void somethingElse(String arg);
}

class RealObject implements Interface {

	@Override
	public void doSomething() {

	}

	@Override
	public void somethingElse(String arg) {

	}
}