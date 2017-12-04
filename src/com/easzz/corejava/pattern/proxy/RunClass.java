package com.easzz.corejava.pattern.proxy;

/**
 * Created by easzz on 2017/8/16 11:16
 */
public class RunClass {
	public static void main(String[] args) {

		IPlay iPlay = new PlayProxy(new Play());
		//用户调用的时候只需要和代理类打交道，减少耦合，
		//实现类专注于实现功能，不关心谁调用，职责更加单一
		//代理类
		iPlay.play();
	}
}
