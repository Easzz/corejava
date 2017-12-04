package com.easzz.corejava.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by easzz on 2017/8/22 16:45
 */
public class MainFramework implements Runnable{
	private CountDownLatch countDownLatch;

	public MainFramework(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println("依赖框架未启动完成...");
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("依赖框架已启动完成，启动主框架....");
	}
}
