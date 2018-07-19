package com.easzz.corejava.pattern.observer;

/**
 * Created by 沈轩 on 2018/6/16 11:12
 */
public class Client {
	public static void main(String[] args){
	    Subject subject=new ConcreteSubject();
	    //创建观察者
		ConcreteObserver concreteObserver=new ConcreteObserver();
		concreteObserver.setType("insert");

		ConcreteObserver concreteObserver1=new ConcreteObserver();
		concreteObserver1.setType("update");

		subject.attacth(concreteObserver);
		subject.attacth(concreteObserver1);
		subject.notifyObservers();

	}
}
