package com.easzz.corejava.pattern.proxy.rmi;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by easzz on 2017/12/2 9:40
 */
public class RemoteServer implements Server {
	@Override
	public String getServer(String name) throws Exception {
		//System.out.println(name);
		return "1";
	}

	public static void main(String[] args) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		service();
	}

	private static void service() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ServerSocket socket = new ServerSocket(8001);
		while (true) {
			Socket accept = socket.accept();
			//BufferedReader br = new BufferedReader(new InputStreamReader(accept.getInputStream()));
			//StringBuilder sb = new StringBuilder();
			//String line = null;
			//while ((line = br.readLine()) != null) {
			//	sb.append(line);
			//}
			//System.out.println("accept string ：" + sb.toString());
			ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
			Call call = (Call) objectInputStream.readObject();
			System.out.println(call);
			Call result = getResult(call);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
			objectOutputStream.writeObject(result);
			objectOutputStream.close();
			objectInputStream.close();

		}
	}

	/**
	 * 执行本地方法并得到返回值。
	 *
	 * @param call
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	private static Call getResult(Call call) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
		String className = call.getClassName();
		String methodName = call.getMethodName();
		Class<?>[] paramType = call.getParamType();
		Object[] params = call.getParams();
		Class<?> aClass = Class.forName(className);
		Method method = aClass.getMethod(methodName, paramType);
		Object invoke = method.invoke(aClass.newInstance(), params);
		call.setResult(invoke);
		return call;
	}
}
