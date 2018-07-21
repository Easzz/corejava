package com.easzz.corejava.pattern.proxy.rmi;

import java.io.*;
import java.net.Socket;

/**
 * Created by easzz on 2017/12/2 9:46
 */
public class Connector {
	private Socket linkSocket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	/**
	 * 获得连接
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public void connect(String host, Integer port) throws IOException {
		linkSocket = new Socket(host, port);
		objectOutputStream = new ObjectOutputStream(linkSocket.getOutputStream());
	}

	/**
	 * 发送请求对象
	 *
	 * @param call
	 * @throws IOException
	 */
	public void sendCall(Call call) throws IOException {
		objectOutputStream.writeObject(call);
	}

	/**
	 * 获取请求对象
	 *
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Call receive() throws IOException, ClassNotFoundException {
		objectInputStream = new ObjectInputStream(linkSocket.getInputStream());
		return (Call) objectInputStream.readObject();
	}

	public void close() {
		try {
			objectInputStream.close();
			objectOutputStream.close();
			linkSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
