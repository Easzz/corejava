package com.easzz.corejava.pattern.proxy.rmi;

/**
 * Created by easzz on 2017/12/2 9:39
 */
public interface Server {
	/**
	 * 获得服务
	 * @throws Exception
	 */
	String getServer(String name) throws Exception;
}
