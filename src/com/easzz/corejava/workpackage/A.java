package com.easzz.corejava.workpackage;

/**
 * Created by easzz on 2017/11/24 9:29
 */
public class A {
	public String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class B extends A {
	public String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		A a=new A();
		a.setName("aaa");
		System.out.println(new B().name);
	}
}
