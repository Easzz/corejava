package com.easzz.corejava.pattern.proxy;

/**
 * Created by easzz on 2017/12/1 8:31
 */
public class Tank1 extends Tank {
	@Override
	public void move() {
		long start = System.currentTimeMillis();
		super.move();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
