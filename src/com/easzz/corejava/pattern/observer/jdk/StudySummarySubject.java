package com.easzz.corejava.pattern.observer.jdk;

import java.util.Observable;

/**
 * Created by 沈轩 on 2018/6/18 22:20
 */
public class StudySummarySubject extends Observable {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.setChanged();
		this.notifyObservers();
	}

}
