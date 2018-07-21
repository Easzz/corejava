package com.easzz.corejava.utils.delegate;

import java.util.function.Function;

/**
 * @Author: 李溪林
 * @Descriptions: 带1个参数的有返回类型的委托
 * @CreateDate: 17-2-20
 */
public class Function1<T, R> {

    /**
     * 委托对象
     */
    private Function<T, R> function;

    /**
     * 构造
     * @param function 委托对象
     */
    public Function1(Function<T, R> function){
        this.function = function;
    }

    /**
     * 执行委托
     * @param t 执行参数
     * @return 委托的返回类型
     */
    public R invoke(T t){
        if(this.function != null){
            return function.apply(t);
        }
        return null;
    }
}
