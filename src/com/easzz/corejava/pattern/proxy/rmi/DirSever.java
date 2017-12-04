package com.easzz.corejava.pattern.proxy.rmi;

/**
 * Created by easzz on 2017/12/3 13:39
 */
public class DirSever implements Server {

	@Override
	public String getServer(String name) throws Exception {
		return "3";
	}
}
