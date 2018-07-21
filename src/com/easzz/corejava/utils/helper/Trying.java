package com.easzz.corejava.utils.helper;

import com.easzz.corejava.utils.extensions.UtilsException;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 异常帮助类
 * Created by 李溪林 on 16-9-19.
 */
public class Trying {

    private static final String currentClassName = "Trying";

    /**
     * 以忽略异常的方式执行指定的动作
     *
     * @param tryAction 要执行的动作
     */
    public static void Try(Runnable tryAction) {
        if (tryAction == null) {
            return;
        }
        try {
            tryAction.run();
        } catch (Exception e) {
        }
    }

    /**
     * 尝试执行指定的动作
     *
     * @param tryAction                要执行的动作
     * @param catchAction              当执行动作发生异常时要执行的动作
     * @param abortOnCatchActionFailed 是否忽略 catchAction 发生的异常
     */
    public static void Try(Runnable tryAction, Consumer<Exception> catchAction, boolean abortOnCatchActionFailed) {
        if (tryAction == null) {
            return;
        }
        try {
            tryAction.run();
        } catch (Exception e) {
            if (catchAction == null) {
                return;
            }
            Runnable aciton = () -> {
                catchAction.accept(e);
            };
            if (!abortOnCatchActionFailed) {
                catchAction.accept(e);
            } else {
                Try(aciton);
            }
        }
    }

    /**
     * 以忽略异常的方式执行指定的动作并返回动作中指定的类型
     *
     * @param supplier     要执行的动作
     * @param defaultValue 当执行的动作发生异常时要返回的默认值
     * @param <R>          动作中要返回的数据类型
     * @return 动作中要返回的数据类型的值
     */
    public static <R> R Try(Supplier<R> supplier, R defaultValue) {
        checkSupplierIsNotNull(supplier, "Try");
        try {
            return supplier.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 检查回调表达式是否为null,为null则抛出异常
     *
     * @param supplier   回调表达式
     * @param methodName 调用方法名
     */
    private static void checkSupplierIsNotNull(Supplier supplier, String methodName) {
        if (supplier == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:回调表达式supplier不能为null.");
        }
    }

    /**
     * 检查回调表达式是否为null,为null则抛出异常
     *
     * @param runnable   回调表达式
     * @param methodName 调用方法名
     */
    private static void checkRunnableIsNotNull(Runnable runnable, String methodName) {
        if (runnable == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:回调表达式runnable不能为null.");
        }
    }
}
