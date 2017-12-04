package com.easzz.corejava.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by easzz on 2017/8/22 16:40
 */
public class SubFramework implements Runnable {
	private String name;
	private CountDownLatch countDownLatch;

	public SubFramework(String name, CountDownLatch countDownLatch) {
		this.name = name;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println(name + "正在启动...");
		try {
			TimeUnit.SECONDS.sleep(new Random().nextInt(10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + "启动完成...");
		this.countDownLatch.countDown();
	}

}
