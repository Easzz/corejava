package com.easzz.corejava.utils.extensions;

import java.util.*;
import java.util.function.Function;

/**
 * @Author: 李溪林
 * @Descriptions: LinkedHashMap工具类
 * @CreateDate: 17/9/29
 */
public class LinkedHashMapUtils {
    private static final String currentClassName = "LinkedHashMapUtils";

    /**
     * 根据指定属性对LinkedHashMap顺序排序,返回新的排序之后的LinkedHashMap对象
     *
     * @param data LinkedHashMap对象
     * @param func 委托
     * @param <K>  LinkedHashMap的Key类型
     * @param <V>  LinkedHashMap的Value类型
     * @param <R>  要排序的属性的数据类型
     * @return 排序后的新LinkedHashMap对象
     */
    public static <K, V, R extends Comparable<? super R>> LinkedHashMap<K, V> sort(LinkedHashMap<K, V> data, Function<Map.Entry<K, V>, R> func) {
        checkMapAndFunctionIsNotNull(data, func, "sort");
        if (data.size() == 0) {
            return new LinkedHashMap<>();
        }
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparing(func);
        //jdk只有List接口实现了sort,因此先转换为List
        List<Map.Entry<K, V>> newData = new ArrayList<>(data.entrySet());
        newData.sort(comparator);

        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        newData.forEach(f -> result.put(f.getKey(), f.getValue()));

        return result;
    }

    /**
     * 检查LinkedHashMap对象是否为null,为null则抛出异常
     *
     * @param data       集合对象
     * @param methodName 调用方法名
     * @param <K>        LinkedHashMap的Key类型
     * @param <V>        LinkedHashMap的Value类型
     */
    private static <K, V> void checkMapIsNotNull(LinkedHashMap<K, V> data, String methodName) {
        if (data == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:LinkedHashMap对象不能为null.");
        }
    }

    /**
     * 检查回调表达式是否为null,为null则抛出异常
     *
     * @param func       回调表达式
     * @param methodName 调用方法名
     * @param <T>        数据类型
     * @param <R>        数据类型
     */
    private static <T, R> void checkFunctionIsNotNull(Function<T, R> func, String methodName) {
        if (func == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:回调表达式不能为null.");
        }
    }

    /**
     * 检查集合和回调表达式是否为null,为null则抛出异常
     *
     * @param arr        集合对象
     * @param func       回调表达式
     * @param methodName 调用方法名
     * @param <K>        LinkedHashMap的Key类型
     * @param <V>        LinkedHashMap的Value类型
     * @param <R>        Function的返回类型
     */
    private static <K, V, R> void checkMapAndFunctionIsNotNull(LinkedHashMap<K, V> arr, Function<Map.Entry<K, V>, R> func, String methodName) {
        checkMapIsNotNull(arr, methodName);
        checkFunctionIsNotNull(func, methodName);
    }
}
