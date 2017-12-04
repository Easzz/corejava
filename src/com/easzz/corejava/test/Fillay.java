package com.easzz.corejava.test;

/**
 * Created by easzz on 2017/8/25 15:01
 */
public class Fillay {
	public static void main(String[] args){
	    try {
	    	throw new NullPointerException("fwef");
		}catch (Exception e){
	    	e.printStackTrace();
		}finally {
			System.out.println("aaa");
		}
	}
}
