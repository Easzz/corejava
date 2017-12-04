package com.easzz.corejava.pattern;

/**
 * Created by Easzz on 2017/1/15.
 */
public class Singleton2 {
    //懒汉式单例模式
    //私有的构造函数
    private Singleton2(){

    }
    private static final Singleton2 s2=new Singleton2();

    //静态工厂模式
    public static Singleton2 getInstance(){
        return  s2;
    }
    public static void ce(String s){
        System.out.println(s);
    }
}
