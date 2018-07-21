package com.easzz.corejava.utils.extensions;


import com.easzz.corejava.utils.enums.SortDirection;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类
 * Created by 李溪林 on 16-8-25.
 */
public class CollectionUtils {

    private static final String currentClassName = "CollectionUtils";

    /**
     * 判断集合是否为空或者长度0
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 为空或者长度0返回true, 否则返回false
     */
    public static <T> boolean isNullOrEmpty(Collection<T> data) {
        return data == null || data.size() == 0;
    }

    /**
     * 将Collection类型的集合转换为List类型的集合
     *
     * @param data Collection类型的集合对象
     * @param <T>  数据类型
     * @return List类型的集合
     */
    public static <T> List<T> toList(Collection<T> data) {
        if (data == null) {
            return null;
        }
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(data);
    }

    /**
     * 对集合中的每个元素都执行指定的动作
     *
     * @param data     集合对象
     * @param consumer 指定的动作
     * @param <T>      集合的类型泛型
     */
    public static <T> void forEach(Collection<T> data, Consumer<T> consumer) {
        if (!isNullOrEmpty(data) && consumer != null) {
            for (T t : data) {
                consumer.accept(t);
            }
        }
    }

    /**
     * 判断集合中所有项是否都符合条件
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 集合中每项都符合条件返回true, 否则返回false
     */
    public static <T> boolean all(Collection<T> data, Predicate<T> predicate) {
        checkCollectionAndPredicateIsNotNull(data, predicate, "all");
        for (T t : data) {
            if (!predicate.test(t)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断集合中是否存在符合条件的项
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在返回true, 否则返回false
     */
    public static <T> boolean any(Collection<T> data, Predicate<T> predicate) {
        checkCollectionAndPredicateIsNotNull(data, predicate, "any");
        for (T t : data) {
            if (predicate.test(t)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 筛选集合中符合条件的项并返回新的集合
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 新的集合
     */
    public static <T> Collection<T> where(Collection<T> data, Predicate<T> predicate) {
        checkCollectionAndPredicateIsNotNull(data, predicate, "where");
        Collection<T> result = new ArrayList<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (predicate.test(item)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * 获取集合中符合条件的第一项,若都不符合条件,则返回null
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 存在符合条件的第一项 或 null
     */
    public static <T> T firstOrDefault(Collection<T> data) {
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
    public static <T> T firstOrDefault(Collection<T> data, Predicate<T> predicate) {
        checkCollectionIsNotNull(data, "firstOrDefault");
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (predicate == null) {
                return item;
            }
            if (predicate.test(item)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 获取集合中符合条件的最后一项,若都不符合条件,则返回null
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 存在符合条件的最后一项 或 null
     */
    public static <T> T lastOrDefault(Collection<T> data) {
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
    public static <T> T lastOrDefault(Collection<T> data, Predicate<T> predicate) {
        checkCollectionIsNotNull(data, "lastOrDefault");
        T result = null;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (predicate == null) {
                result = item;
                continue;
            }
            if (predicate.test(item)) {
                result = item;
            }
        }

        return result;
    }

    /**
     * 获取集合中存在符合条件的项的数量
     *
     * @param data      集合对象
     * @param predicate 谓词筛选器
     * @param <T>       数据类型
     * @return 存在符合条件的项的数量
     */
    public static <T> int size(Collection<T> data, Predicate<T> predicate) {
        checkCollectionIsNotNull(data, "size");
        if (predicate == null) {
            return data.size();
        }
        int count = 0;
        for (T t : data) {
            if (predicate.test(t)) {
                count++;
            }
        }

        return count;
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
    public static <T, R> Collection<R> select(Collection<T> data, Function<T, R> func) {
        checkCollectionAndFunctionIsNotNull(data, func, "select");
        Collection<R> result = new ArrayList<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            R key = func.apply(item);
            result.add(key);
        }

        return result;
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
    public static <T, R> Map<R, T> toMap(Collection<T> data, Function<T, R> func) {
        checkCollectionAndFunctionIsNotNull(data, func, "toMap");
        Map<R, T> result = new HashMap<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            R key = func.apply(item);
            if (!result.containsKey(key)) {
                result.put(key, item);
            }
        }

        return result;
    }

    /**
     * 将集合以指定属性作为key,指定属性作为value组装成Map对象
     *
     * @param data      集合对象
     * @param funcKey   key的委托表达式
     * @param funcValue value的委托表达式
     * @param <T>       数据类型
     * @param <Key>     key的数据类型
     * @param <Value>   value的数据类型
     * @return 指定属性作为key, 指定属性作为value的Map对象
     */
    public static <T, Key, Value> Map<Key, Value> toMap(Collection<T> data, Function<T, Key> funcKey, Function<T, Value> funcValue) {
        checkCollectionAndFunctionIsNotNull(data, funcKey, "toMap");
        checkFunctionIsNotNull(funcValue, "toMap");
        Map<Key, Value> result = new HashMap<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            Key key = funcKey.apply(item);
            if (!result.containsKey(key)) {
                result.put(key, funcValue.apply(item));
            }
        }

        return result;
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
    public static <T, R> Map<R, Collection<T>> groupBy(Collection<T> data, Function<T, R> func) {
        checkCollectionAndFunctionIsNotNull(data, func, "groupBy");
        Map<R, Collection<T>> result = new HashMap<>();
        Collection<T> temp;
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
     * 将集合按指定属性分组并组装成Map返回
     *
     * @param data    集合对象
     * @param keyFunc key委托
     * @param singleValueFunc 单个value委托
     * @param <T>     数据类型
     * @param <K>     表示key的指定属性的数据类型
     * @param <V>     表示value的指定属性的数据类型
     * @return 一个字典, key是指定属性的数据类型, value是该集合类型
     */
    public static <T, K, V> Map<K, Collection<V>> groupBy(Collection<T> data, Function<T, K> keyFunc, Function<T, V> singleValueFunc) {
        checkCollectionAndFunctionIsNotNull(data, keyFunc, "groupBy");
        Map<K, Collection<V>> result = new HashMap<>();
        Collection<V> temp;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            K key = keyFunc.apply(item);
            V value = singleValueFunc.apply(item);

            if (result.containsKey(key)) {
                temp = result.get(key);
                temp.add(value);
            } else {
                temp = new ArrayList<>();
                temp.add(value);
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
    public static <T> Collection<T> take(Collection<T> data, int count) {
        checkCollectionIsNotNull(data, "take");
        int len = data.size();
        if (count > len) {
            return new ArrayList<>(data);
        }
        List<T> result = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        int index = 0;
        while (index < count && iterator.hasNext()) {
            T item = iterator.next();
            result.add(item);
            index++;
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
    public static <T> Collection<T> skip(Collection<T> data, int count) {
        checkCollectionIsNotNull(data, "skip");
        int len = data.size();
        if (count > len) {
            return new ArrayList<>(data);
        }
        if (count < 0) {
            count = 0;
        }
        Collection<T> result = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (index >= count) {
                result.add(item);
            }

            index++;
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
    public static <T extends Comparable<? super T>> Collection<T> sort(Collection<T> data) {
        checkCollectionIsNotNull(data, "sort");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing((item -> item));
        //jdk只有List接口实现了sort,因此先转换为List
        List<T> newData = toList(data);
        newData.sort(comparator);

        return new ArrayList<>(newData);
    }

    /**
     * 根据指定属性对集合倒序排序,返回新的排序之后的集合对象
     * 注意:此sort方法中的 T 必须实现 java.lang.Comparable 接口,如 Integer 等包装类
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 排序后的新集合
     */
    public static <T extends Comparable<? super T>> Collection<T> sortDescending(Collection<T> data) {
        checkCollectionIsNotNull(data, "sortDescending");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> temp = Comparator.comparing((item -> item));
        Comparator<T> comparator = temp.reversed();
        //jdk只有List接口实现了sort,因此先转换为List
        List<T> newData = toList(data);
        newData.sort(comparator);

        return new ArrayList<>(newData);
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
    public static <T, R extends Comparable<? super R>> Collection<T> sort(Collection<T> data, Function<T, R> func) {
        checkCollectionAndFunctionIsNotNull(data, func, "sort");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing(func);
        //jdk只有List接口实现了sort,因此先转换为List
        List<T> newData = toList(data);
        newData.sort(comparator);

        return new ArrayList<>(newData);
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
    public static <T, R extends Comparable<? super R>> Collection<T> sortDescending(Collection<T> data, Function<T, R> func) {
        checkCollectionAndFunctionIsNotNull(data, func, "sortDescending");
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        Comparator<T> comparator = Comparator.comparing(func).reversed();
        List<T> newData = toList(data);
        newData.sort(comparator);

        return new ArrayList<>(newData);
    }

    /**
     * 对集合进行求和
     * 注意:此sum方法中的 T 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 和.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T sum(Collection<T> data) {
        checkCollectionIsNotNull(data, "sum");
        Integer integerResult = null;
        Long longResult = null;
        Short shortResult = null;
        Double doubleResult = null;
        Float floatResult = null;
        BigDecimal bigDecimalResult = null;
        boolean isInteger = false, isLong = false, isShort = false, isDouble = false, isFloat = false, isBigDecimal = false;
        Class<? extends Number> rClass = null;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (item != null) {
                rClass = item.getClass();
                if (isInteger) {
                    if (integerResult != null) {
                        integerResult += item.intValue();
                    } else {
                        integerResult = item.intValue();
                    }
                    continue;
                }
                if (isLong) {
                    if (longResult != null) {
                        longResult += item.longValue();
                    } else {
                        longResult = item.longValue();
                    }
                    continue;
                }
                if (isShort) {
                    if (shortResult != null) {
                        shortResult = (short) (shortResult.shortValue() + item.shortValue());
                    } else {
                        shortResult = item.shortValue();
                    }
                    continue;
                }
                if (isDouble) {
                    if (doubleResult != null) {
                        doubleResult += item.doubleValue();
                    } else {
                        doubleResult = item.doubleValue();
                    }
                    continue;
                }
                if (isFloat) {
                    if (floatResult != null) {
                        floatResult += item.floatValue();
                    } else {
                        floatResult = item.floatValue();
                    }
                    continue;
                }
                if (isBigDecimal) {
                    if (bigDecimalResult != null) {
                        bigDecimalResult = bigDecimalResult.add(new BigDecimal(item.toString()));
                    } else {
                        bigDecimalResult = new BigDecimal(item.toString());
                    }
                    continue;
                }
                if (rClass == Integer.class || rClass == int.class) {
                    isInteger = true;
                    if (integerResult != null) {
                        integerResult += item.intValue();
                    } else {
                        integerResult = item.intValue();
                    }
                    continue;
                }
                if (rClass == Long.class || rClass == long.class) {
                    isLong = true;
                    if (longResult != null) {
                        longResult += item.longValue();
                    } else {
                        longResult = item.longValue();
                    }
                    continue;
                }
                if (rClass == Short.class || rClass == short.class) {
                    isShort = true;
                    if (shortResult != null) {
                        shortResult = (short) (shortResult.shortValue() + item.shortValue());
                    } else {
                        shortResult = item.shortValue();
                    }
                    continue;
                }
                if (rClass == Double.class || rClass == double.class) {
                    isDouble = true;
                    if (doubleResult != null) {
                        doubleResult += item.doubleValue();
                    } else {
                        doubleResult = item.doubleValue();
                    }
                    continue;
                }
                if (rClass == Float.class || rClass == float.class) {
                    isFloat = true;
                    if (floatResult != null) {
                        floatResult += item.floatValue();
                    } else {
                        floatResult = item.floatValue();
                    }
                    continue;
                }
                if (rClass == BigDecimal.class) {
                    isBigDecimal = true;
                    if (bigDecimalResult != null) {
                        bigDecimalResult = bigDecimalResult.add(new BigDecimal(item.toString()));
                    } else {
                        bigDecimalResult = new BigDecimal(item.toString());
                    }
                    continue;
                }
            }
        }
        if (isInteger) {
            return (T) integerResult;
        }
        if (isLong) {
            return (T) longResult;
        }
        if (isShort) {
            return (T) shortResult;
        }
        if (isDouble) {
            return (T) doubleResult;
        }
        if (isFloat) {
            return (T) floatResult;
        }
        if (isBigDecimal) {
            return (T) bigDecimalResult;
        }
        return null;
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
    public static <T, R extends Number> R sum(Collection<T> data, Function<T, R> func) {
        checkCollectionIsNotNullOrEmpty(data, "sum");
        checkFunctionIsNotNull(func, "sum");

        Collection<R> rs = new ArrayList<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T temp = each.next();
            R item = func.apply(temp);
            rs.add(item);
        }
        return sum(rs);
    }

    /**
     * 获取集合中最大的值
     * 注意:此max方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 最大值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T max(Collection<T> data) {
        checkCollectionIsNotNull(data, "max");
        Integer integerResult = null;
        Long longResult = null;
        Short shortResult = null;
        Double doubleResult = null;
        Float floatResult = null;
        BigDecimal bigDecimalResult = null;
        boolean isInteger = false, isLong = false, isShort = false, isDouble = false, isFloat = false, isBigDecimal = false;
        Class<? extends Number> rClass = null;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (item == null) {
                continue;
            }
            rClass = item.getClass();
            if (isInteger) {
                if (integerResult != null) {
                    integerResult = Math.max(integerResult.intValue(), item.intValue());
                } else {
                    integerResult = item.intValue();
                }
                continue;
            }
            if (isLong) {
                if (longResult != null) {
                    longResult = Math.max(longResult.longValue(), item.longValue());
                } else {
                    longResult = item.longValue();
                }
                continue;
            }
            if (isShort) {
                if (shortResult != null) {
                    shortResult = (short) Math.max(shortResult.shortValue(), item.shortValue());
                } else {
                    shortResult = item.shortValue();
                }
                continue;
            }
            if (isDouble) {
                if (doubleResult != null) {
                    doubleResult = Math.max(doubleResult.doubleValue(), item.doubleValue());
                } else {
                    doubleResult = item.doubleValue();
                }
                continue;
            }
            if (isFloat) {
                if (floatResult != null) {
                    floatResult = Math.max(floatResult.floatValue(), item.floatValue());
                } else {
                    floatResult = item.floatValue();
                }
                continue;
            }
            if (isBigDecimal) {
                if (bigDecimalResult != null) {
                    BigDecimal tempBigDecimal = new BigDecimal(item.toString());
                    bigDecimalResult = bigDecimalResult.compareTo(tempBigDecimal) > 0 ? bigDecimalResult : tempBigDecimal;
                } else {
                    bigDecimalResult = new BigDecimal(item.toString());
                }
                continue;
            }
            if (rClass == Integer.class || rClass == int.class) {
                isInteger = true;
                if (integerResult != null) {
                    integerResult = Math.max(integerResult.intValue(), item.intValue());
                } else {
                    integerResult = item.intValue();
                }
                continue;
            }
            if (rClass == Long.class || rClass == long.class) {
                isLong = true;
                if (longResult != null) {
                    longResult = Math.max(longResult.longValue(), item.longValue());
                } else {
                    longResult = item.longValue();
                }
                continue;
            }
            if (rClass == Short.class || rClass == short.class) {
                isShort = true;
                if (shortResult != null) {
                    shortResult = (short) Math.max(shortResult.shortValue(), item.shortValue());
                } else {
                    shortResult = item.shortValue();
                }
                continue;
            }
            if (rClass == Double.class || rClass == double.class) {
                isDouble = true;
                if (doubleResult != null) {
                    doubleResult = Math.max(doubleResult.doubleValue(), item.doubleValue());
                } else {
                    doubleResult = item.doubleValue();
                }
                continue;
            }
            if (rClass == Float.class || rClass == float.class) {
                isFloat = true;
                if (floatResult != null) {
                    floatResult = Math.max(floatResult.floatValue(), item.floatValue());
                } else {
                    floatResult = item.floatValue();
                }
                continue;
            }
            if (rClass == BigDecimal.class) {
                isBigDecimal = true;
                if (bigDecimalResult != null) {
                    BigDecimal tempBigDecimal = new BigDecimal(item.toString());
                    bigDecimalResult = bigDecimalResult.compareTo(tempBigDecimal) > 0 ? bigDecimalResult : tempBigDecimal;
                } else {
                    bigDecimalResult = new BigDecimal(item.toString());
                }
                continue;
            }
        }
        if (isInteger) {
            return (T) integerResult;
        }
        if (isLong) {
            return (T) longResult;
        }
        if (isShort) {
            return (T) shortResult;
        }
        if (isDouble) {
            return (T) doubleResult;
        }
        if (isFloat) {
            return (T) floatResult;
        }
        if (isBigDecimal) {
            return (T) bigDecimalResult;
        }
        return null;
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
    public static <T, R extends Number> R max(Collection<T> data, Function<T, R> func) {
        checkCollectionIsNotNullOrEmpty(data, "max");
        checkFunctionIsNotNull(func, "max");
        Collection<R> rs = new ArrayList<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T temp = each.next();
            R item = func.apply(temp);
            rs.add(item);
        }
        return max(rs);
    }

    /**
     * 获取集合中最小的值
     * 注意:此min方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 最小值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T min(Collection<T> data) {
        checkCollectionIsNotNull(data, "min");
        Integer integerResult = null;
        Long longResult = null;
        Short shortResult = null;
        Double doubleResult = null;
        Float floatResult = null;
        BigDecimal bigDecimalResult = null;
        boolean isInteger = false, isLong = false, isShort = false, isDouble = false, isFloat = false, isBigDecimal = false;
        Class<? extends Number> rClass = null;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (item == null) {
                continue;
            }
            rClass = item.getClass();
            if (isInteger) {
                if (integerResult != null) {
                    integerResult = Math.min(integerResult.intValue(), item.intValue());
                } else {
                    integerResult = item.intValue();
                }
                continue;
            }
            if (isLong) {
                if (longResult != null) {
                    longResult = Math.min(longResult.longValue(), item.longValue());
                } else {
                    longResult = item.longValue();
                }
                continue;
            }
            if (isShort) {
                if (shortResult != null) {
                    shortResult = (short) Math.min(shortResult.shortValue(), item.shortValue());
                } else {
                    shortResult = item.shortValue();
                }
                continue;
            }
            if (isDouble) {
                if (doubleResult != null) {
                    doubleResult = Math.min(doubleResult.doubleValue(), item.doubleValue());
                } else {
                    doubleResult = item.doubleValue();
                }
                continue;
            }
            if (isFloat) {
                if (floatResult != null) {
                    floatResult = Math.min(floatResult.floatValue(), item.floatValue());
                } else {
                    floatResult = item.floatValue();
                }
                continue;
            }
            if (isBigDecimal) {
                if (bigDecimalResult != null) {
                    BigDecimal tempBigDecimal = new BigDecimal(item.toString());
                    bigDecimalResult = bigDecimalResult.compareTo(tempBigDecimal) > 0 ? tempBigDecimal : bigDecimalResult;
                } else {
                    bigDecimalResult = new BigDecimal(item.toString());
                }
                continue;
            }
            if (rClass == Integer.class || rClass == int.class) {
                isInteger = true;
                if (integerResult != null) {
                    integerResult = Math.min(integerResult.intValue(), item.intValue());
                } else {
                    integerResult = item.intValue();
                }
                continue;
            }
            if (rClass == Long.class || rClass == long.class) {
                isLong = true;
                if (longResult != null) {
                    longResult = Math.min(longResult.longValue(), item.longValue());
                } else {
                    longResult = item.longValue();
                }
                continue;
            }
            if (rClass == Short.class || rClass == short.class) {
                isShort = true;
                if (shortResult != null) {
                    shortResult = (short) Math.min(shortResult.shortValue(), item.shortValue());
                } else {
                    shortResult = item.shortValue();
                }
                continue;
            }
            if (rClass == Double.class || rClass == double.class) {
                isDouble = true;
                if (doubleResult != null) {
                    doubleResult = Math.min(doubleResult.doubleValue(), item.doubleValue());
                } else {
                    doubleResult = item.doubleValue();
                }
                continue;
            }
            if (rClass == Float.class || rClass == float.class) {
                isFloat = true;
                if (floatResult != null) {
                    floatResult = Math.min(floatResult.floatValue(), item.floatValue());
                } else {
                    floatResult = item.floatValue();
                }
                continue;
            }
            if (rClass == BigDecimal.class) {
                isBigDecimal = true;
                if (bigDecimalResult != null) {
                    BigDecimal tempBigDecimal = new BigDecimal(item.toString());
                    bigDecimalResult = bigDecimalResult.compareTo(tempBigDecimal) > 0 ? tempBigDecimal : bigDecimalResult;
                } else {
                    bigDecimalResult = new BigDecimal(item.toString());
                }
                continue;
            }
        }
        if (isInteger) {
            return (T) integerResult;
        }
        if (isLong) {
            return (T) longResult;
        }
        if (isShort) {
            return (T) shortResult;
        }
        if (isDouble) {
            return (T) doubleResult;
        }
        if (isFloat) {
            return (T) floatResult;
        }
        if (isBigDecimal) {
            return (T) bigDecimalResult;
        }
        return null;
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
    public static <T, R extends Number> R min(Collection<T> data, Function<T, R> func) {
        checkCollectionIsNotNullOrEmpty(data, "min");
        checkFunctionIsNotNull(func, "min");
        Collection<R> rs = new ArrayList<>();
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T temp = each.next();
            R item = func.apply(temp);
            rs.add(item);
        }
        return min(rs);
    }

    /**
     * 获取集合求平均值
     * 注意:此average方法中的 R 必须继承 java.lang.Number 类,目前只支持对 Integer Long Short Double Float BigDecimal 及其基本类型 的识别
     *
     * @param data 集合对象
     * @param <T>  数据类型
     * @return 平均值.当每个元素均为null时则返回null.
     */
    public static <T extends Number> T average(Collection<T> data) {
        checkCollectionIsNotNullOrEmpty(data, "average");
        Integer integerResult = null;
        Long longResult = null;
        Short shortResult = null;
        Double doubleResult = null;
        Float floatResult = null;
        BigDecimal bigDecimalResult = null;
        boolean isInteger = false, isLong = false, isShort = false, isDouble = false, isFloat = false, isBigDecimal = false;
        Class<? extends Number> rClass = null;
        Iterator<T> each = data.iterator();
        while (each.hasNext()) {
            T item = each.next();
            if (item != null) {
                rClass = item.getClass();
                if (isInteger) {
                    if (integerResult != null) {
                        integerResult += item.intValue();
                    } else {
                        integerResult = item.intValue();
                    }
                    continue;
                }
                if (isLong) {
                    if (longResult != null) {
                        longResult += item.longValue();
                    } else {
                        longResult = item.longValue();
                    }
                    continue;
                }
                if (isShort) {
                    if (shortResult != null) {
                        shortResult = (short) (shortResult.shortValue() + item.shortValue());
                    } else {
                        shortResult = item.shortValue();
                    }
                    continue;
                }
                if (isDouble) {
                    if (doubleResult != null) {
                        doubleResult += item.doubleValue();
                    } else {
                        doubleResult = item.doubleValue();
                    }
                    continue;
                }
                if (isFloat) {
                    if (floatResult != null) {
                        floatResult += item.floatValue();
                    } else {
                        floatResult = item.floatValue();
                    }
                    continue;
                }
                if (isBigDecimal) {
                    if (bigDecimalResult != null) {
                        bigDecimalResult = bigDecimalResult.add(new BigDecimal(item.toString()));
                    } else {
                        bigDecimalResult = new BigDecimal(item.toString());
                    }
                    continue;
                }
                if (rClass == Integer.class || rClass == int.class) {
                    isInteger = true;
                    if (integerResult != null) {
                        integerResult += item.intValue();
                    } else {
                        integerResult = item.intValue();
                    }
                    continue;
                }
                if (rClass == Long.class || rClass == long.class) {
                    isLong = true;
                    if (longResult != null) {
                        longResult += item.longValue();
                    } else {
                        longResult = item.longValue();
                    }
                    continue;
                }
                if (rClass == Short.class || rClass == short.class) {
                    isShort = true;
                    if (shortResult != null) {
                        shortResult = (short) (shortResult.shortValue() + item.shortValue());
                    } else {
                        shortResult = item.shortValue();
                    }
                    continue;
                }
                if (rClass == Double.class || rClass == double.class) {
                    isDouble = true;
                    if (doubleResult != null) {
                        doubleResult += item.doubleValue();
                    } else {
                        doubleResult = item.doubleValue();
                    }
                    continue;
                }
                if (rClass == Float.class || rClass == float.class) {
                    isFloat = true;
                    if (floatResult != null) {
                        floatResult += item.floatValue();
                    } else {
                        floatResult = item.floatValue();
                    }
                    continue;
                }
                if (rClass == BigDecimal.class) {
                    isBigDecimal = true;
                    if (bigDecimalResult != null) {
                        bigDecimalResult = bigDecimalResult.add(new BigDecimal(item.toString()));
                    } else {
                        bigDecimalResult = new BigDecimal(item.toString());
                    }
                    continue;
                }
            }
        }
        int length = data.size();
        if (isInteger) {
            integerResult = integerResult / length;
            return (T) integerResult;
        }
        if (isLong) {
            longResult = longResult / length;
            return (T) longResult;
        }
        if (isShort) {
            shortResult = (short) (shortResult / length);
            return (T) shortResult;
        }
        if (isDouble) {
            doubleResult = doubleResult / length;
            return (T) doubleResult;
        }
        if (isFloat) {
            floatResult = floatResult / length;
            return (T) floatResult;
        }
        if (isBigDecimal) {
            bigDecimalResult = bigDecimalResult.divide(new BigDecimal(length));
            return (T) bigDecimalResult;
        }
        return null;
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
    public static <T, R extends Number> R average(Collection<T> data, Function<T, R> func) {
        checkCollectionIsNotNullOrEmpty(data, "average");
        checkFunctionIsNotNull(func, "average");

        Collection<R> rs = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            T temp = iterator.next();
            R item = func.apply(temp);
            rs.add(item);
        }
        return average(rs);
    }

    /**
     * 对集合进行分页,返回新的集合
     *
     * @param data 要处理的集合对象
     * @param page 页码
     * @param size 每页条数
     * @param <T>  集合元素的数据类型
     * @param <R>  排序属性的数据类型
     * @return 分页后的新集合
     */
    public static <T, R extends Comparable<? super R>> Collection<T> splitPager(Collection<T> data, int page, int size) {
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
     * @return 分页后的新集合
     */
    public static <T, R extends Comparable<? super R>> Collection<T> splitPager(Collection<T> data, int page, int size, Function<T, R> func) {
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
     * @return 分页后的新集合
     */
    public static <T, R extends Comparable<? super R>> Collection<T> splitPager(Collection<T> data, int page, int size, Function<T, R> func, SortDirection sortDirection) {
        if (data == null) {
            return null;
        }
        if (page <= 0 || size < 0) {
            throw new UtilsException(currentClassName + "中的方法[splitPager]发生异常:分页参数错误.");
        }
        if (sortDirection == null) {
            sortDirection = SortDirection.ASCENDING;
        }
        Collection<T> result = take(skip((func == null ? data : (sortDirection.getKey() == SortDirection.ASCENDING.getKey() ? sort(data, func) : sortDescending(data, func))), (page - 1) * size), size);
        return result;
    }

    /**
     * 将 TSource 集合转换成 TResult 集合
     *
     * @param source      源对象集合
     * @param resultClass 目标对象类型
     * @param <TSource>   源集合元素数据类型
     * @param <TResult>   目标集合元素数据类型
     * @return 目标类型的对象集合
     */
    public static <TSource, TResult> Collection<TResult> castTo(Collection<TSource> source, Class<TResult> resultClass) {
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
     * @return 目标类型的对象集合
     */
    public static <TSource, TResult> Collection<TResult> castTo(Collection<TSource> source, Class<TResult> resultClass, BiConsumer<TSource, TResult> consumer) {
        if (source == null) {
            return null;
        }
        if (resultClass == null) {
            throw new UtilsException(currentClassName + "中的方法[castTo]发生异常:目标类型不能为null.");
        }
        Collection<TResult> result = new ArrayList<>();
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
     * 检查集合对象是否为null,为null则抛出异常
     *
     * @param data       集合对象
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkCollectionIsNotNull(Collection<T> data, String methodName) {
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
    private static <T> void checkCollectionIsNotNullOrEmpty(Collection<T> data, String methodName) {
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
    private static <T> void checkCollectionAndPredicateIsNotNull(Collection<T> data, Predicate<T> predicate, String methodName) {
        checkCollectionIsNotNull(data, methodName);
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
    private static <T, R> void checkCollectionAndFunctionIsNotNull(Collection<T> arr, Function<T, R> func, String methodName) {
        checkCollectionIsNotNull(arr, methodName);
        checkFunctionIsNotNull(func, methodName);
    }

    public static <T extends Number> T newSum(Collection<T> data) {
        checkCollectionIsNotNull(data, "newSum");
        Double sumVal = data.stream()
                .filter(t -> t != null && t instanceof Number)
                .map(String::valueOf)
                .mapToDouble(Double::valueOf)
                .sum();
        T item = null;
        try {
            item = data.stream().filter(t -> t != null).findFirst().get();
        } catch (Exception e) {
            return null;
        }

        if (item.getClass() == Integer.class) {
            return (T) Integer.valueOf(sumVal.intValue());
        }
        if (item.getClass() == Double.class) {
            return (T) sumVal;
        }
        if (item.getClass() == Short.class) {
            return (T) Short.valueOf(sumVal.shortValue());
        }
        if (item.getClass() == Float.class) {
            return (T) Float.valueOf(sumVal.floatValue());
        }
        if (item.getClass() == Long.class) {
            return (T) Long.valueOf(sumVal.longValue());
        }
        if (item.getClass() == BigDecimal.class) {
            return (T) BigDecimal.valueOf(sumVal.longValue());
        }
        throw new UtilsException(currentClassName + "中的方法newSum发生异常:无法识别T的类型.");
    }
}
