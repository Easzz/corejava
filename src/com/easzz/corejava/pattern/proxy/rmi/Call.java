package com.easzz.corejava.pattern.proxy.rmi;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by easzz on 2017/12/1 10:57
 */
public class Call implements Serializable{

	//调用的类名
	private String className;
	//调用的方法名
	private String methodName;
	//方法参数类型
	private Class<?>[] paramType;
	//方法参数
	private Object[] params;

	private Object result;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Call() {
	}

	public Call(String className, String methodName, Class<?>[] paramType, Object[] params) {
		this.className = className;
		this.methodName = methodName;
		this.paramType = paramType;
		this.params = params;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParamType() {
		return paramType;
	}

	public void setParamType(Class<?>[] paramType) {
		this.paramType = paramType;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "Call{" +
				"className='" + className + '\'' +
				", methodName='" + methodName + '\'' +
				", paramType=" + Arrays.toString(paramType) +
				", params=" + Arrays.toString(params) +
				'}';
	}
}