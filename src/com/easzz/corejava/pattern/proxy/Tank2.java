package com.easzz.corejava.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by easzz on 2017/12/1 8:32
 */
public class Tank2 extends Tank1 {
	public static void main(String[] args) {
		InvocationHandler handler=new MyHandler();
		MyHandler a= (MyHandler) Proxy.newProxyInstance(MyHandler.class.getClass().getClassLoader(), new Class[]{Moveable.class}, handler);

	}
}

class MyHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}
}