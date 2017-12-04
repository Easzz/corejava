package com.easzz.corejava.hash;

import java.util.Map;

/**
 * Created by easzz on 2017/10/20 9:16
 */
public class HashMap {
	public static void main(String[] args) {
		//对象作为hashmap的key ，必须重写equals和hashCode()方法
		Student s1 = new Student("mike");
		Student s2=new Student("mike");
		Map<Student,Object> m1=new java.util.HashMap<>();
		m1.put(s1,"1");
		m1.put(s2,"1");
		System.out.println(m1);

	}
}

class Student {
	private String name;

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student{" +
				"name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Student student = (Student) o;

		return name != null ? name.equals(student.name) : student.name == null;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
