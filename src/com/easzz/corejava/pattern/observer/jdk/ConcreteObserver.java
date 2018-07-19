package com.easzz.corejava.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by 沈轩 on 2018/6/18 22:18
 */
public class ConcreteObserver implements Observer {
	private String observerName;

	public String getObserverName() {
		return observerName;
	}

	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(((StudySummarySubject)o).getContent());
	}
}
