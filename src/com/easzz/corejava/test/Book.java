package com.easzz.corejava.test;

/**
 * Created by easzz on 2017/8/25 15:56
 */
class Book{
	static Book book = new Book();
	static int a;
	static int b = 0;
	private Book(){
		System.out.println("a"+a);
		System.out.println("b"+b);
		a++;
		b++;
		System.out.println("a"+a);
		System.out.println("b"+b);
	}
	public static void main(String[] args){
		Book book = new Book();
		System.out.println(book.a);
		System.out.println(book.b);
	}
}