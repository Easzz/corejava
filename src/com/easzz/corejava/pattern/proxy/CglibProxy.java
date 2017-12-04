package com.easzz.corejava.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by easzz on 2017/11/1 15:12
 * 使用cglib来实现动态代理
 */


public class CglibProxy implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	public Object getInstance(Class clazz) {
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("before....");
		Object o1 = methodProxy.invokeSuper(o, objects);
		System.out.println("after...");
		return o1;
	}
}

class Hello {
	public static void main(String[] args) {
		CglibProxy cglibProxy = new CglibProxy();
		SayHello instance = (SayHello) cglibProxy.getInstance(SayHello.class);
		instance.say();
	}
}

/**
 * 这是一个需要被代理的类，也就是父类，通过字节码技术创建这个类的子类，实现动态代理
 */
class SayHello {
	void say() {
		System.out.println("say...");
	}
}