package com.easzz.corejava.generic;

/**
 * Created by 沈轩 on 2018/7/9 22:52
 */
public class Type<T,Q> {
	private T first;
	private Q secend;

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public Q getSecend() {
		return secend;
	}

	public void setSecend(Q secend) {
		this.secend = secend;
	}

	private Type() {
	}

	public Type(T first, Q secend) {
		this.first = first;
		this.secend = secend;
	}

	public Type of(){
		return new Type(first,secend);
	}
}
