package com.easzz.corejava.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by easzz on 2017/10/20 15:07
 */
public class Quean {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final A a = new A(new ArrayList<String>());

		final CountDownLatch count = new CountDownLatch(4);
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 4; i++) {
			final int finalI = i;
			Future<Integer> a1 = executorService.submit(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					try {
						TimeUnit.SECONDS.sleep(new Random().nextInt(1));
						a.setIds("a" + finalI);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("a");
					count.countDown();
					return finalI;
				}
			});
			Object o = a1.get();
			System.out.println(o);
		}
		count.await();
		List<String> ids = a.getIds();
		System.out.println(ids);
	}
}

class A {
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(String id) {
		this.ids.add(id);
	}

	public A(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "A{" +
				"ids=" + ids +
				'}';
	}
}