package com.easzz.corejava.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 沈轩 on 2018/6/16 10:33
 * 目标对象，它知道观察它的观察者，并提供注册（添加）和删除观察者的接口
 */
public class Subject{
	/**
	 * 用来保存注册的观察者对象
	 */
	private List<Observer> observers = new ArrayList<>();

	public void attacth(Observer observer) {
		observers.add(observer);
	}

	public void detach(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * 通知所有注册的观察者对象
	 */
	protected void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}
