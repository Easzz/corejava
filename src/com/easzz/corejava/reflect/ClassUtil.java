package com.easzz.corejava.reflect;

import java.lang.reflect.Method;

/**
 * Created by Easzz on 2017/1/7.
 */
public class ClassUtil {
    /** sdfwerwer
     * 打印类的成员变量，包括成员函数，成员变量
     * @param oj
     */
    public static void printClssMessage(Object oj){
        //要获取类的信息，首先要获取类的类类型
        Class c = oj.getClass();
        //获取类的名称
        System.out.println("类的名称是："+c.getName());
        /**
         * Method类，方法对象
         * 一个成员方法就是一个Method对象
         * getMethods获取所有类的方法，包括父类继承的
         * getDeclaredMethods 获取该类自己声明的方法
         */
        System.out.println("自己声明的方法有：");
        Method[] declaredMethods = c.getDeclaredMethods();
        for(Method m:declaredMethods){
            Class<?> returnType = m.getReturnType();
            System.out.print(returnType.getName()+" ");
            Class<?>[] parameterTypes = m.getParameterTypes();
            System.out.print(m.getName()+"(");
            for(Class c2:parameterTypes){
                System.out.print(c2.getName()+",");
            }
            System.out.println(")");
        }

        System.out.println("===========");
        System.out.println("继承的方法有：");
        Method[] ms=c.getMethods();
        for (int i=0;i<ms.length;i++){
            //得到方法的返回值类型
            Class returnType = ms[i].getReturnType();
            System.out.print(returnType.getName()+" ");
            String methodName = ms[i].getName();
            System.out.print(methodName+"(");
            //获取参数
            Class<?>[] parameterTypes = ms[i].getParameterTypes();
            for(Class c1:parameterTypes){
                System.out.print(c1.getName()+",");
            }
            System.out.println(")");

        }
    }
}
