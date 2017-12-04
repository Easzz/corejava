package com.easzz.corejava.pattern;

/**
 * Created by Easzz on 15/06/2017.
 * 内部静态类实现单例模式
 */
public class Singleton3 {
    private Singleton3(){}
    public static class Holder{
         static Singleton3 singleton3=new Singleton3();
    }
    public static Singleton3 getInstance(){
        return Holder.singleton3;
    }
}
