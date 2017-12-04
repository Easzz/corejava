package com.easzz.corejava.pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/10/24 9:36
 * 利用动态代理 在sayHello方法前后输出日志。
 * 在运行时，在内存中生成新类（代理类），在调用
 * ，这个类在调用sayHello() 或者add()方法的时候，
 * 其实调用的是LoggerHanlder的invoke 方法， 而那个invoke就会拦截真正的方法调用，添加日志功能了！ ”
 */
public class DynamicProxy {
	public static void main(String[] args) {
		IHelloWord helloWord = new HelloWorld();
		LogHandler logHandler = new LogHandler(helloWord);
		//生成代理类，利用代理类来操作真正的方法
		IHelloWord proxy = (IHelloWord) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				helloWord.getClass().getInterfaces(), logHandler);
		proxy.sayHello();
	}

}

interface IHelloWord {
	void sayHello();
}

class HelloWorld implements IHelloWord {

	@Override
	public void sayHello() {
		System.out.println("hello");
	}
}

class LogHandler implements InvocationHandler {
	private Object object;

	public LogHandler(Object object) {
		this.object = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//LogHandler相当于一个中间层，我们自动成的$HelloWorld100类会调用它，即通过methed.invoke调用

		System.out.println("start...");
		Object invoke = method.invoke(object, args);
		System.out.println("end");
		return invoke;
	}
}