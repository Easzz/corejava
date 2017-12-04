package com.easzz.corejava.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by easzz on 2017/8/22 16:36
 */
public class CountDownlatchDemo {
	public static void main(String[] args){
		ExecutorService executorService= Executors.newCachedThreadPool();
		CountDownLatch countDownLatch=new CountDownLatch(4);
		SubFramework subFramework1=new SubFramework("sub1",countDownLatch);
		SubFramework subFramework2=new SubFramework("sub2",countDownLatch);
		SubFramework subFramework3=new SubFramework("sub3",countDownLatch);
		SubFramework subFramework4=new SubFramework("sub4",countDownLatch);
		MainFramework mainFramework=new MainFramework(countDownLatch);

		executorService.submit(mainFramework);
		executorService.execute(subFramework3);
		executorService.execute(subFramework4);
		executorService.execute(subFramework1);
		executorService.execute(subFramework2);

		executorService.shutdown();
	}
}
