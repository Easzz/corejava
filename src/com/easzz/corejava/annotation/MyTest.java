package com.easzz.corejava.annotation;

/**
 * Created by Easzz on 07/07/2017.
 */
public class MyTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class aClass = Class.forName("com.easzz.corejava.annotation.Apple");
        /*Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation:annotations){
            System.out.println(annotation.toString());
        }*/
        Apple o = (Apple) aClass.newInstance();//调用无参的构造方法
        o.displayName();
        /*Field[] fields = aClass.getDeclaredFields();
        for (Field f:fields){
            System.out.println(f.getName());
        }*/
    }
}
