package com.easzz.corejava.pattern;

/**
 * Created by Easzz on 2017/1/15.
 */
public class Singleton {
    //饿汉式单例模式
    private static Singleton uniqueInstance=null;
    private Singleton(){ }
    //双重检查锁
    public static Singleton getSingleton(){
        if (uniqueInstance==null){
            //若两个线程同时进入if，第一个线程进入synchronized,创建对象，第二个线程阻塞，
            //当第一个线程退出时，需要再次判断是否为null，否则第二个线程还是会创建对象。
            synchronized (Singleton.class){
                if (uniqueInstance==null){
                    uniqueInstance=new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
