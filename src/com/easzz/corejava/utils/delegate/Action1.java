package com.easzz.corejava.utils.delegate;

import java.util.function.Consumer;

/**
 * @Author: 李溪林
 * @Descriptions: 带1个参数的无返回类型委托
 * @CreateDate: 17-2-20
 */
public class Action1<T> {

    /**
     * 委托对象
     */
    private Consumer<T> consumer;

    /**
     * 构造
     * @param consumer 委托对象
     */
    public Action1(Consumer<T> consumer){
        this.consumer = consumer;
    }

    /**
     * 执行委托
     * @param t 执行参数
     */
    public void invoke(T t){
        if(this.consumer != null){
            consumer.accept(t);
        }
    }
}
