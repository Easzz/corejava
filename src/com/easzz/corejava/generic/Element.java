package com.easzz.corejava.generic;

/**
 * Created by 沈轩 on 2018/7/9 22:46
 */
public class Element<T> {
	private T name;

	public T getName() {
		return name;
	}

	public void setName(T name) {
		this.name = name;
	}
}
