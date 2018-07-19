package com.easzz.corejava.pattern.observer;

/**
 * Created by 沈轩 on 2018/6/16 10:34
 * 具体的目标对象，负责把有关状态存入到相应的观察者
 */
public class ConcreteSubject extends Subject{
	private String subjectState;

	public String getSubjectState() {
		return subjectState;
	}

	public void setSubjectState(String subjectState) {
		this.subjectState = subjectState;
		this.notifyObservers();
	}
}
