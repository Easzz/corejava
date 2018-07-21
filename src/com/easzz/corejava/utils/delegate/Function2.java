package com.easzz.corejava.utils.delegate;

import java.util.function.BiFunction;

/**
 * @Author: 李溪林
 * @Descriptions: 带2个参数的有返回类型的委托
 * @CreateDate: 17-2-20
 */
public class Function2<T1, T2, R> {

    /**
     * 委托对象
     */
    private BiFunction<T1, T2, R> function;

    /**
     * 构造
     * @param function 委托对象
     */
    public Function2(BiFunction<T1, T2, R> function){
        this.function = function;
    }

    /**
     * 执行委托
     * @param t1 执行参数
     * @param t2 执行参数
     * @return 委托的返回类型
     */
    public R invoke(T1 t1, T2 t2){
        if(this.function != null){
            return function.apply(t1, t2);
        }
        return null;
    }
}
