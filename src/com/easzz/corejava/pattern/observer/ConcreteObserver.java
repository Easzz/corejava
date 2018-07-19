package com.easzz.corejava.pattern.observer;

/**
 * Created by 沈轩 on 2018/6/16 10:35
 * 具体的观察者对象，实现更新的方法，使自身的状态和目标的状态保持一致
 */
public class ConcreteObserver implements Observer {

	private String observerState;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void update(Subject subject) {
		observerState = ((ConcreteSubject) subject).getSubjectState();
		System.out.println("type=" + type);
	}

	public String getObserverState() {
		return observerState;
	}

	public void setObserverState(String observerState) {
		this.observerState = observerState;
	}
}
