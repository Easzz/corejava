package com.easzz.corejava.utils.extensions;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 数组工具类
 * Created by 李溪林 on 16-8-11.
 */
public class ArrayUtils {

    private static final String currentClassName = "ArrayUtils";

    /**
     * 判断数组是否为null或长度为0
     *
     * @param arr 要判定的数组
     * @param <T> 数据类型
     * @return 为null或长度为0返回true, 否则返回false
     */
    public static <T> boolean isNullOrEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 将数组转换成List
     *
     * @param arr 要转换的数组
     * @param <T> 数据类型
     * @return List
     */
    public static <T> List<T> toList(T[] arr) {
        if (arr == null) {
            return null;
        }
        return arr.length == 0 ? new ArrayList<T>() : new ArrayList<T>(Arrays.asList(arr));
    }

    /**
     * 将数组转化成Set
     *
     * @param arr 要转换的数组
     * @param <T> 数据类型
     * @return Set
     */
    public static <T> Set<T> toSet(T[] arr) {
        if (arr == null) {
            return null;
        }
        return arr.length == 0 ? new HashSet<T>() : new HashSet<T>(Arrays.asList(arr));
    }

    /**
     * 将字符串数组转化成整型数组
     *
     * @param arr 要转化的字符串数组
     * @return 整型数组
     */
    public static Integer[] toIntArray(String[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new Integer[0];
        }
        return toIntArray(arr, true);
    }

    /**
     * 将字符串数组转化成整型数组
     *
     * @param arr                    要转化的字符串数组
     * @param ignoreConvertException 是否忽略转化异常
     * @return 整型数组
     */
    public static Integer[] toIntArray(String[] arr, boolean ignoreConvertException) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new Integer[0];
        }

        List<Integer> result = new ArrayList<>();
        try {
            for (String item : arr) {
                Integer temp = Integer.parseInt(item);
                result.add(temp);
            }
        } catch (Exception e) {
            if (!ignoreConvertException) {
                throw new UtilsException(currentClassName + "中的方法[toIntArray]发生数据类型转换异常.", e);
            }
        }

        return result.toArray(new Integer[result.size()]);
    }

    /**
     * 将字符串数组转化成长整型数组
     *
     * @param arr 要转化的字符串数组
     * @return 长整型数组
     */
    public static Long[] toLongArray(String[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new Long[0];
        }
        return toLongArray(arr, true);
    }

    /**
     * 将字符串数组转化成长整型数组
     *
     * @param arr                    要转化的字符串数组
     * @param ignoreConvertException 是否忽略转化异常
     * @return 长整型数组
     */
    public static Long[] toLongArray(String[] arr, boolean ignoreConvertException) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new Long[0];
        }

        List<Long> result = new ArrayList<>();
        try {
            for (String item : arr) {
                Long temp = Long.parseLong(item);
                result.add(temp);
            }
        } catch (Exception e) {
            if (!ignoreConvertException) {
                throw new UtilsException(currentClassName + "中的方法[toLongArray]发生数据类型转换异常.", e);
            }
        }

        return result.toArray(new Long[result.size()]);
    }

    /**
     * 判断数组中所有项是否都符合条件
     *
     * @param arr       数组对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 集合中每项都符合条件返回true, 否则返回false
     */
    public static <T> boolean all(T[] arr, Predicate<T> predicate) {
        checkArrayAndPredicateIsNotNull(arr, predicate, "all");
        for (T t : arr) {
            if (!predicate.test(t)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断数组中是否存在符合条件的项
     *
     * @param arr       数组对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在返回true, 否则返回false
     */
    public static <T> boolean any(T[] arr, Predicate<T> predicate) {
        checkArrayAndPredicateIsNotNull(arr, predicate, "any");
        for (T t : arr) {
            if (predicate.test(t)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取数组中符合条件的第一项,若都不符合条件,则返回null
     *
     * @param arr 数组对象
     * @param <T> 数据类型
     * @return 存在符合条件的第一项 或 null
     */
    public static <T> T firstOrDefault(T[] arr) {
        return firstOrDefault(arr, null);
    }

    /**
     * 获取数组中符合条件的第一项,若都不符合条件,则返回null
     *
     * @param arr       数组对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的第一项 或 null
     */
    public static <T> T firstOrDefault(T[] arr, Predicate<T> predicate) {
        checkArrayIsNotNull(arr, "firstOrDefault");
        for (T t : arr) {
            if (predicate == null) {
                return t;
            }
            if (predicate.test(t)) {
                return t;
            }
        }

        return null;
    }

    /**
     * 获取数组中符合条件的最后一项,若都不符合条件,则返回null
     *
     * @param arr 数组对象
     * @param <T> 数据类型
     * @return 存在符合条件的最后一项 或 null
     */
    public static <T> T lastOrDefault(T[] arr) {
        return lastOrDefault(arr, null);
    }

    /**
     * 获取数组中符合条件的最后一项,若都不符合条件,则返回null
     *
     * @param arr       数组对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的最后一项 或 null
     */
    public static <T> T lastOrDefault(T[] arr, Predicate<T> predicate) {
        checkArrayIsNotNull(arr, "lastOrDefault");
        T result = null;
        for (T t : arr) {
            if (predicate == null) {
                result = t;
                continue;
            }
            if (predicate.test(t)) {
                result = t;
            }
        }

        return result;
    }

    /**
     * 获取数组中存在符合条件的项的数量
     *
     * @param arr       数组对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的项的数量
     */
    public static <T> int size(T[] arr, Predicate<T> predicate) {
        checkArrayIsNotNull(arr, "size");
        if (predicate == null) {
            return arr.length;
        }
        int count = 0;
        for (T t : arr) {
            if (predicate.test(t)) {
                count++;
            }
        }

        return count;
    }

    /**
     * 将数组以指定属性作为key,元素对象作为value组装成Map对象
     *
     * @param arr  数组对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  key的数据类型
     * @return 指定属性作为key, 元素对象作为value的Map对象
     */
    public static <T, R> Map<R, T> toMap(T[] arr, Function<T, R> func) {
        checkArrayAndFunctionIsNotNull(arr, func, "toMap");
        Map<R, T> result = new HashMap<>();
        for (T t : arr) {
            R key = func.apply(t);
            if (!result.containsKey(key)) {
                result.put(key, t);
            }
        }

        return result;
    }

    /**
     * 将数组以指定属性作为key,指定属性作为value组装成Map对象
     *
     * @param arr       数组对象
     * @param funcKey   key的委托表达式
     * @param funcValue value的委托表达式
     * @param <T>       数据类型
     * @param <Key>     key的数据类型
     * @param <Value>   value的数据类型
     * @return 指定属性作为key, 指定属性作为value的Map对象
     */
    public static <T, Key, Value> Map<Key, Value> toMap(T[] arr, Function<T, Key> funcKey, Function<T, Value> funcValue) {
        checkArrayAndFunctionIsNotNull(arr, funcKey, "toMap");
        checkFunctionIsNotNull(funcValue, "toMap");
        Map<Key, Value> result = new HashMap<>();
        for (T t : arr) {
            Key key = funcKey.apply(t);
            if (!result.containsKey(key)) {
                result.put(key, funcValue.apply(t));
            }
        }

        return result;
    }

    /**
     * 检查数组对象是否为null,为null则抛出异常
     *
     * @param arr        数组对象
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkArrayIsNotNull(T[] arr, String methodName) {
        if (arr == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:数组对象不能为null.");
        }
    }

    /**
     * 检查谓词筛选器是否为null,为null则抛出异常
     *
     * @param predicate  谓词筛选器
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkPredicateIsNotNull(Predicate<T> predicate, String methodName) {
        if (predicate == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:谓词筛选器不能为null.");
        }
    }

    /**
     * 检查数组和谓词筛选器是否为null,为null则抛出异常
     *
     * @param arr        数组对象
     * @param predicate  谓词筛选器
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkArrayAndPredicateIsNotNull(T[] arr, Predicate<T> predicate, String methodName) {
        checkArrayIsNotNull(arr, methodName);
        checkPredicateIsNotNull(predicate, methodName);
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
     * 检查数组和回调表达式是否为null,为null则抛出异常
     *
     * @param arr        数组对象
     * @param func       回调表达式
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T, R> void checkArrayAndFunctionIsNotNull(T[] arr, Function<T, R> func, String methodName) {
        checkArrayIsNotNull(arr, methodName);
        checkFunctionIsNotNull(func, methodName);
    }
}
