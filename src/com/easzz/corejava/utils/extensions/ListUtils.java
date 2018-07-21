package com.easzz.corejava.utils.extensions;


import com.easzz.corejava.utils.enums.SortDirection;
import com.easzz.corejava.utils.helper.Trying;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类
 * Created by 李溪林 on 16-8-11.
 */
public class ListUtils {

    private static final String currentClassName = "ListUtils";

    /**
     * 判断集合是否为空或者长度0
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 为空或者长度0返回true, 否则返回false
     */
    public static <T> boolean isNullOrEmpty(List<T> data) {
        return data == null || data.size() == 0;
    }

    /**
     * 将List集合转化成Set集合
     *
     * @param data 要转化的List集合
     * @param <T>  数据类型
     * @return Set集合
     */
    public static <T> Set<T> toSet(List<T> data) {
        if (data == null) {
            return null;
        }
        if (data.size() == 0) {
            return new HashSet<>();
        }
        Set<T> set = new HashSet<>();
        set.addAll(data);

        return set;
    }

    /**
     * 对集合中的每个元素都执行指定的动作
     * @param data 集合对象
     * @param consumer 指定的动作
     * @param <T> 集合的类型泛型
     */
    public static <T> void forEach(List<T> data, Consumer<T> consumer){
        CollectionUtils.forEach(data, consumer);
    }

    /**
     * 判断集合中所有项是否都符合条件
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 集合中每项都符合条件返回true, 否则返回false
     */
    public static <T> boolean all(List<T> data, Predicate<T> predicate) {
        checkListAndPredicateIsNotNull(data, predicate, "all");
        return CollectionUtils.all(data, predicate);
    }

    /**
     * 判断集合中是否存在符合条件的项
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在返回true, 否则返回false
     */
    public static <T> boolean any(List<T> data, Predicate<T> predicate) {
        checkListAndPredicateIsNotNull(data, predicate, "any");
        return CollectionUtils.any(data, predicate);
    }

    /**
     * 筛选集合中符合条件的项并返回新的集合
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 新的集合
     */
    public static <T> List<T> where(List<T> data, Predicate<T> predicate) {
        checkListAndPredicateIsNotNull(data, predicate, "where");

        return CollectionUtils.toList(CollectionUtils.where(data, predicate));
    }

    /**
     * 获取集合中符合条件的第一项,若都不符合条件,则返回null
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 存在符合条件的第一项 或 null
     */
    public static <T> T firstOrDefault(List<T> data) {
        return firstOrDefault(data, null);
    }

    /**
     * 获取集合中符合条件的第一项,若都不符合条件,则返回null
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的第一项 或 null
     */
    public static <T> T firstOrDefault(List<T> data, Predicate<T> predicate) {
        checkListIsNotNull(data, "firstOrDefault");
        return CollectionUtils.firstOrDefault(data, predicate);
    }

    /**
     * 获取集合中符合条件的最后一项,若都不符合条件,则返回null
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 存在符合条件的最后一项 或 null
     */
    public static <T> T lastOrDefault(List<T> data) {
        return lastOrDefault(data, null);
    }

    /**
     * 获取集合中符合条件的最后一项,若都不符合条件,则返回null
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的最后一项 或 null
     */
    public static <T> T lastOrDefault(List<T> data, Predicate<T> predicate) {
        checkListIsNotNull(data, "lastOrDefault");
        return CollectionUtils.lastOrDefault(data, predicate);
    }

    /**
     * 获取集合中存在符合条件的项的数量
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的项的数量
     */
    public static <T> int size(List<T> data, Predicate<T> predicate) {
        checkListIsNotNull(data, "size");
        return CollectionUtils.size(data, predicate);
    }

    /**
     * 将集合中的指定属性组装成新的集合并返回
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  指定属性的数据类型
     * @return 指定属性的集合
     */
    public static <T, R> List<R> select(List<T> data, Function<T, R> func) {
        checkListAndFunctionIsNotNull(data, func, "select");
        return CollectionUtils.toList(CollectionUtils.select(data, func));
    }

    /**
     * 将集合以指定属性作为key,元素对象作为value组装成Map对象
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  key的数据类型
     * @return 指定属性作为key, 元素对象作为value的Map对象
     */
    public static <T, R> Map<R, T> toMap(List<T> data, Function<T, R> func) {
        checkListAndFunctionIsNotNull(data, func, "toMap");

        return CollectionUtils.toMap(data, func);
    }

    /**
     * 将集合以指定属性作为key,指定属性作为value组装成Map对象
     *
     * @param data       集合对象
     * @param funcKey   key的委托表达式
     * @param funcValue value的委托表达式
     * @param <T>       数据类型
     * @param <Key>     key的数据类型
     * @param <Value>   value的数据类型
     * @return 指定属性作为key, 指定属性作为value的Map对象
     */
    public static <T, Key, Value> Map<Key, Value> toMap(List<T> data, Function<T, Key> funcKey, Function<T, Value> funcValue) {
        checkListAndFunctionIsNotNull(data, funcKey, "toMap");
        checkFunctionIsNotNull(funcValue, "toMap");

        return CollectionUtils.toMap(data, funcKey, funcValue);
    }

    /**
     * 将集合按指定属性分组并组装成Map返回
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  指定属性的数据类型
     * @return 一个字典, key是指定属性的数据类型, value是该集合类型
     */
    public static <T, R> Map<R, List<T>> groupBy(List<T> data, Function<T, R> func) {
        checkListAndFunctionIsNotNull(data, func, "groupBy");
        Map<R, List<T>> result = new HashMap<>();
        List<T> temp;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            R key = func.apply(item);

            if (result.containsKey(key)) {
                temp = result.get(key);
                temp.add(item);
            } else {
                temp = new ArrayList<>();
                temp.add(item);
                result.put(key, temp);
            }
        }

        return result;
    }

    /**
     * 截取集合中的指定数量的元素,返回新的集合
     *
     * @param data  原集合
     * @param count 要截取的数据量
     * @param <T>   数据类型
     * @return 截取后的新集合
     */
    public static <T> List<T> take(List<T> data, int count) {
        checkListIsNotNull(data, "take");
        int len = data.size();
        if (count > len) {
            return new ArrayList<>(data);
        }
        List<T> result = new ArrayList<>();
        for (int k = 0; k < count && k < len; k++) {

            result.add(data.get(k));
        }

        return result;
    }

    /**
     * 略过集合中的指定数量的元素,返回新的集合
     *
     * @param data  原集合
     * @param count 要忽略的数据量
     * @param <T>   数据类型
     * @return 新集合
     */
    public static <T> List<T> skip(List<T> data, int count) {
        checkListIsNotNull(data, "take");
        List<T> result = new ArrayList<>();
        int len = data.size();
        if (count > len) {
            return result;
        }
        if (count < 0) {
            count = 0;
        }
        for (int k = count; k < len; k++) {
            result.add(data.get(k));
        }

        return result;
    }

    /**
     * 根据指定属性对集合顺序排序,返回新的排序之后的集合对象
     * 注意:此sort方法中的 T 必须实现 java.lang.Comparable 接口,如 Integer 等包装类
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 排序后的新集合
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> data) {
        checkListIsNotNull(data, "sort");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing((item -> item));
        data.sort(comparator);

        return new ArrayList<>(data);
    }

    /**
     * 根据指定属性对集合倒序排序,返回新的排序之后的集合对象
     * 注意:此sort方法中的 T 必须实现 java.lang.Comparable 接口,如 Integer 等包装类
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 排序后的新集合
     */
    public static <T extends Comparable<? super T>> List<T> sortDescending(List<T> data) {
        checkListIsNotNull(data, "sortDescending");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> temp = Comparator.comparing((item -> item));
        Comparator<T> comparator = temp.reversed();
        data.sort(comparator);

        return new ArrayList<>(data);
    }

    /**
     * 根据指定属性对集合顺序排序,返回新的排序之后的集合对象
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  要排序的属性的数据类型
     * @return 排序后的新集合
     */
    public static <T, R extends Comparable<? super R>> List<T> sort(List<T> data, Function<T, R> func) {
        checkListAndFunctionIsNotNull(data, func, "sort");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing(func);
        data.sort(comparator);
        return new ArrayList<>(data);
    }

    /**
     * 根据指定属性对集合倒序排序,返回新的排序之后的集合对象
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  要排序的属性的数据类型
     * @return 排序后的新集合
     */
    public static <T, R extends Comparable<? super R>> List<T> sortDescending(List<T> data, Function<T, R> func) {
        checkListAndFunctionIsNotNull(data, func, "sortDescending");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing(func).reversed();
        data.sort(comparator);
        return new ArrayList<>(data);
    }

    /**
     * 对集合进行求和
     * 注意:此sum方法中的 T 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 和.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T sum(List<T> data) {
        checkListIsNotNull(data, "sum");
        return CollectionUtils.sum(data);
    }

    /**
     * 对集合中指定类型的属性进行求和
     * 注意:此sum方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  数据类型
     * @return 指定属性的和.当集合size为0时将抛出异常.
     */
    public static <T, R extends Number> R sum(List<T> data, Function<T, R> func) {
        checkListIsNotNullOrEmpty(data, "sum");
        checkFunctionIsNotNull(func, "sum");
        return CollectionUtils.sum(data, func);
    }

    /**
     * 获取集合中最大的值
     * 注意:此max方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 最大值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T max(List<T> data) {
        checkListIsNotNull(data, "max");
        return CollectionUtils.max(data);
    }

    /**
     * 获取集合中最大的值
     * 注意:此max方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  数据类型
     * @return 最大值.当集合size为0时将抛出异常.
     */
    public static <T, R extends Number> R max(List<T> data, Function<T, R> func) {
        checkListIsNotNullOrEmpty(data, "max");
        checkFunctionIsNotNull(func, "max");
        return CollectionUtils.max(data, func);
    }

    /**
     * 获取集合中最小的值
     * 注意:此min方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 最小值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T min(List<T> data) {
        checkListIsNotNull(data, "min");
        return CollectionUtils.min(data);
    }

    /**
     * 获取集合中最小的值
     * 注意:此max方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  数据类型
     * @return 最小值.当集合size为0时将抛出异常.
     */
    public static <T, R extends Number> R min(List<T> data, Function<T, R> func) {
        checkListIsNotNullOrEmpty(data, "min");
        checkFunctionIsNotNull(func, "min");
        return CollectionUtils.min(data, func);
    }

    /**
     * 获取集合求平均值
     * 注意:此average方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 平均值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T average(List<T> data) {
        checkListIsNotNullOrEmpty(data, "average");
        return CollectionUtils.average(data);
    }

    /**
     * 获取集合中指定属性的平均值
     * 注意:此average方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  数据类型
     * @return 平均值.当集合size为0时将抛出异常.
     */
    public static <T, R extends Number> R average(List<T> data, Function<T, R> func) {
        checkListIsNotNullOrEmpty(data, "average");
        checkFunctionIsNotNull(func, "average");
        return CollectionUtils.average(data, func);
    }

    /**
     * 对集合进行分页,返回新的集合
     *
     * @param data 要处理的集合对象
     * @param page 页码
     * @param size 每页条数
     * @param <T>  集合元素的数据类型
     * @param <R>  排序属性的数据类型
     * @return 分页后的集合
     */
    public static <T, R extends Comparable<? super R>> List<T> splitPager(List<T> data, int page, int size) {
        return splitPager(data, page, size, null);
    }

    /**
     * 对集合进行排序和分页,返回新的集合
     *
     * @param data 要处理的集合对象
     * @param page 页码
     * @param size 每页条数
     * @param func 排序表达式
     * @param <T>  集合元素的数据类型
     * @param <R>  排序属性的数据类型
     * @return 分页后的集合
     */
    public static <T, R extends Comparable<? super R>> List<T> splitPager(List<T> data, int page, int size, Function<T, R> func) {
        return splitPager(data, page, size, func, SortDirection.ASCENDING);
    }

    /**
     * 对集合进行排序和分页,返回新的集合
     *
     * @param data          要处理的集合对象
     * @param page          页码
     * @param size          每页条数
     * @param func          排序表达式
     * @param sortDirection 排序方式
     * @param <T>           集合元素的数据类型
     * @param <R>           排序属性的数据类型
     * @return 分页后的集合
     */
    public static <T, R extends Comparable<? super R>> List<T> splitPager(List<T> data, int page, int size, Function<T, R> func, SortDirection sortDirection) {
        if (data == null) {
            return null;
        }
        if (page <= 0 || size < 0) {
            throw new UtilsException(currentClassName + "中的方法[splitPager]发生异常:分页参数错误.");
        }
        if (sortDirection == null) {
            sortDirection = SortDirection.ASCENDING;
        }
        List<T> result = take(skip((func == null ? data : (sortDirection.getKey() == SortDirection.ASCENDING.getKey() ? sort(data, func) : sortDescending(data, func))), (page - 1) * size), size);
        return result;
    }

    /**
     * 将 TSource 集合转换成 TResult 集合
     *
     * @param source      源对象集合
     * @param resultClass 目标对象类型
     * @param <TSource>   源集合元素数据类型
     * @param <TResult>   目标集合元素数据类型
     * @return 转换后的目标对象集合
     */
    public static <TSource, TResult> List<TResult> castTo(List<TSource> source, Class<TResult> resultClass) {
        return castTo(source, resultClass, null);
    }

    /**
     * 将 TSource 集合转换成 TResult 集合
     *
     * @param source      源对象集合
     * @param resultClass 目标对象类型
     * @param consumer    额外的赋值表达式
     * @param <TSource>   源集合元素数据类型
     * @param <TResult>   目标集合元素数据类型
     * @return 转换后的目标对象集合
     */
    public static <TSource, TResult> List<TResult> castTo(List<TSource> source, Class<TResult> resultClass, BiConsumer<TSource, TResult> consumer) {
        if (source == null) {
            return null;
        }
        if (resultClass == null) {
            throw new UtilsException(currentClassName + "中的方法[castTo]发生异常:目标类型不能为null.");
        }
        List<TResult> result = new ArrayList<>();
        for (TSource item : source) {
            TResult target = null;
            try {
                target = resultClass.newInstance();
            } catch (Exception e) {
                throw new UtilsException(currentClassName + "中的方法[castTo]发生异常:目标类型无法初始化.");
            }
            TResult temp = ObjectUtils.copyPropertyTo(item, target, consumer);
            result.add(temp);
        }

        return result;
    }

    /**
     * 对集合进行分批执行指定动作
     *
     * @param data                     要处理的完整集合
     * @param size                     每批处理的元素数量
     * @param tryAction                批处理执行的动作
     * @param catchAction              批处理发生异常时执行的动作
     * @param abortOnCatchActionFailed 是否忽略 catchAction 发生的异常
     * @param <T>                      要处理的集合的数据类型
     */
    public static <T> void batch(List<T> data, int size, Consumer<List<T>> tryAction, Consumer<Exception> catchAction, boolean abortOnCatchActionFailed) {
        if (isNullOrEmpty(data) || tryAction == null) {
            return;
        }
        int times = data.size() / size + (data.size() % size == 0 ? 0 : 1);
        for (int index = 1; index <= times; index++) {
            List<T> tempData = take(skip(data, (index - 1) * size), size);

            try {
                tryAction.accept(tempData);
            } catch (Exception e) {
                if (catchAction != null) {
                    if (!abortOnCatchActionFailed) {
                        catchAction.accept(e);
                    } else {
                        Runnable aciton = () -> {
                            catchAction.accept(e);
                        };
                        Trying.Try(aciton);
                    }
                }
            }
        }
    }

    /**
     * 对集合进行分批执行指定动作
     *
     * @param data                     要处理的完整集合
     * @param size                     每批处理的元素数量
     * @param tryAction                批处理执行的动作
     * @param catchAction              批处理发生异常时执行的动作
     * @param abortOnCatchActionFailed 是否忽略 catchAction 发生的异常
     * @param <T>                      要处理的集合的数据类型
     */
    public static <T> void batch(List<T> data, int size, Consumer<List<T>> tryAction, BiConsumer<Exception, List<T>> catchAction, boolean abortOnCatchActionFailed) {
        if (isNullOrEmpty(data) || tryAction == null) {
            return;
        }
        int times = data.size() / size + (data.size() % size == 0 ? 0 : 1);
        for (int index = 1; index <= times; index++) {
            List<T> tempData = take(skip(data, (index - 1) * size), size);

            try {
                tryAction.accept(tempData);
            } catch (Exception e) {
                if (catchAction != null) {
                    if (!abortOnCatchActionFailed) {
                        catchAction.accept(e, tempData);
                    } else {
                        Runnable aciton = () -> {
                            catchAction.accept(e, tempData);
                        };
                        Trying.Try(aciton);
                    }
                }
            }
        }
    }

    /**
     * 对集合进行分批执行指定动作,并可在动作中指定返回值
     *
     * @param data                     要处理的完整集合
     * @param size                     每批处理的元素数量
     * @param tryAction                批处理执行的动作
     * @param catchAction              批处理发生异常时执行的动作
     * @param abortOnCatchActionFailed 是否忽略 catchAction 发生的异常
     * @param <T>                      要处理的集合的数据类型
     * @param <R>                      批处理执行的动作的返回类型
     * @return 每批数据执行动作后的返回类型对象集合
     */
    public static <T, R> List<R> batchWithReturn(List<T> data, int size, Function<List<T>, R> tryAction, Consumer<Exception> catchAction, boolean abortOnCatchActionFailed) {
        if (data == null) {
            return null;
        }
        List<R> results = new ArrayList<>();
        if (isNullOrEmpty(data) || tryAction == null) {
            return results;
        }
        int times = data.size() / size + (data.size() % size == 0 ? 0 : 1);
        for (int index = 1; index <= times; index++) {
            List<T> tempData = take(skip(data, (index - 1) * size), size);

            try {
                R r = tryAction.apply(tempData);
                results.add(r);
            } catch (Exception e) {
                if (catchAction != null) {
                    if (!abortOnCatchActionFailed) {
                        catchAction.accept(e);
                    } else {
                        Runnable aciton = () -> {
                            catchAction.accept(e);
                        };
                        Trying.Try(aciton);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 重组主从数据
     * @param mainData 主数据集合
     * @param mainFunction 主数据唯一键的属性的委托表达式
     * @param detailData 从数据集合
     * @param detailFunction 从数据中指向主数据唯一键的属性的委托表达式
     * @param setter 将从数据集合设置给主数据的委托表达式
     * @param <T1> 主数据数据类型
     * @param <T2> 从数据数据类型
     * @param <R> 主数据唯一键的属性的数据类型
     */
    public static <T1, T2, R> void rebuildMainDetail(List<T1> mainData, Function<T1, R> mainFunction, List<T2> detailData, Function<T2, R> detailFunction, BiConsumer<T1, List<T2>> setter){
        if(isNullOrEmpty(mainData) || isNullOrEmpty(detailData)){
            return;
        }
        checkFunctionIsNotNull(mainFunction, "rebuildMainDetail");
        checkFunctionIsNotNull(detailFunction, "rebuildMainDetail");
        if (setter == null) {
            throw new UtilsException(currentClassName + "中的方法[rebuildMainDetail]发生异常:回调表达式setter不能为null.");
        }

        Map<R, List<T2>> detailMaps = groupBy(detailData, detailFunction);
        Iterator<T1> iterators = mainData.iterator();
        while (iterators.hasNext()){
            T1 item = iterators.next();
            R key = mainFunction.apply(item);
            if (detailMaps.containsKey(key)) {
                List<T2> details = detailMaps.get(key);
                setter.accept(item, details);
            }
        }
    }

    /**
     * 检查集合对象是否为null,为null则抛出异常
     *
     * @param data       集合对象
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkListIsNotNull(List<T> data, String methodName) {
        if (data == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:集合对象不能为null.");
        }
    }

    /**
     * 检查集合对象是否为null或空,为null或空则抛出异常
     *
     * @param data       集合对象
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkListIsNotNullOrEmpty(List<T> data, String methodName) {
        if (isNullOrEmpty(data)) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:集合对象不能为null,也不能为空.");
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
     * 检查集合和谓词筛选器是否为null,为null则抛出异常
     *
     * @param data       集合对象
     * @param predicate  谓词筛选器
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkListAndPredicateIsNotNull(List<T> data, Predicate<T> predicate, String methodName) {
        checkListIsNotNull(data, methodName);
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
     * 检查集合和回调表达式是否为null,为null则抛出异常
     *
     * @param arr        集合对象
     * @param func       回调表达式
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T, R> void checkListAndFunctionIsNotNull(List<T> arr, Function<T, R> func, String methodName) {
        checkListIsNotNull(arr, methodName);
        checkFunctionIsNotNull(func, methodName);
    }
}
