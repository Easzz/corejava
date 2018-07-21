package com.easzz.corejava.utils.delegate;


/**
 * @Author: 李溪林
 * @Descriptions: 无参数无返回类型的委托
 * @CreateDate: 17-2-20
 */
public class Action0 {

    /**
     * 委托对象
     */
    private Runnable runnable;

    /**
     * 构造
     * @param runnable 委托对象
     */
    public Action0(Runnable runnable){
        this.runnable = runnable;
    }

    /**
     * 执行委托
     */
    public void invoke(){
        if(this.runnable != null){
            runnable.run();
        }
    }
}
