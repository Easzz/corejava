package com.easzz.corejava.exception;

/**
 * Created by 沈轩 on 2018/7/7 23:22
 */
public class Client {
	public static void main(String[] args) {
		try {
			test();
			System.out.println(21212);
		} catch (MyException e) {
			String message = e.getMessage();System.out.println(message);
		}
		
	}

	private static void test() throws MyException {
		System.out.println(1);
		if (1==1){
			throw new MyException("123;");
		}
	}
}
