package com.easzz.corejava.pattern.observer.jdk;

/**
 * Created by 沈轩 on 2018/6/18 22:23
 */
public class Client {
	public static void main(String[] args){
	    StudySummarySubject studySummarySubject=new StudySummarySubject();
	    ConcreteObserver cus=new ConcreteObserver();
	    studySummarySubject.addObserver(cus);

		studySummarySubject.setContent("123");
	}
}
