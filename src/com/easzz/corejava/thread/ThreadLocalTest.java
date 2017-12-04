package com.easzz.corejava.thread;

public class ThreadLocalTest {
	private static final ThreadLocal<Integer> VALUE = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new MyThread(i)).start();
		}
	}

	static class MyThread implements Runnable {
		private int index;

		public MyThread(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			System.out.println("线程" + index + "的初始value:" + VALUE.get());
			for (int i = 0; i < 10; i++) {
				VALUE.set(VALUE.get() + i);
			}
			System.out.println("线程" + index + "的累加value:" + VALUE.get());
		}
	}
}
