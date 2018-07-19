package com.easzz.corejava.pattern.observer;

/**
 * Created by 沈轩 on 2018/6/16 10:35
 * 观察者接口，定义一个更新的接口给哪些目标改变时，被通知的对象
 */
public interface Observer {
  	void update(Subject subject);
}
