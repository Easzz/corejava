package com.easzz.corejava.thread;

import java.util.concurrent.Executors;

/**
 * Created by easzz on 2017/11/23 17:23
 */
public class ThreadTest {
	public static void main(String[] args){
		//返回的线程池没有线程上限，有可能导致内存OOM
		//要使用固定线程上限的线程池
		Executors.newCachedThreadPool();
	}
}
