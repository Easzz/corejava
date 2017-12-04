package com.easzz.corejava.utils;

/**
 * Created by easzz on 2017/11/9 15:51
 */
public class ObjectCopy {
	public static void main(String[] args) throws CloneNotSupportedException {
		Student s=new Student();
		s.setAge(11);
		Student s1=new Student();
		s1= (Student) s.clone();
		System.out.println(s1.getAge());
		s.setAge(12);
		System.out.println(s1.getAge());
		System.out.println(s==s1);
	}
}

class Student implements Cloneable{
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Student s=null;
		s= (Student) super.clone();
		return s;
	}
}