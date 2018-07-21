package com.easzz.corejava.utils.delegate;

import java.util.function.Supplier;

/**
 * @Author: 李溪林
 * @Descriptions: 无参数的有返回类型的委托
 * @CreateDate: 17-2-20
 */
public class Function0<T> {

    /**
     * 委托对象
     */
    private Supplier<T> supplier;

    /**
     * 构造
     * @param supplier 委托对象
     */
    public Function0(Supplier<T> supplier){
        this.supplier = supplier;
    }

    /**
     * 执行委托
     * @return 委托的返回类型
     */
    public T invoke(){
        if(this.supplier != null){
            return supplier.get();
        }
        return null;
    }
}
