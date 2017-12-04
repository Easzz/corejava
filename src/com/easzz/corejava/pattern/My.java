package com.easzz.corejava.pattern;

/**
 * Created by Easzz on 2017/1/15.
 */
public class My {
    public static void main(String[] args){
        Singleton2.getInstance();
        Singleton2 ss = Singleton2.getInstance();
        Singleton2 instance = Singleton2.getInstance();
        System.out.println(ss);
        System.out.println(instance);
    }

}
