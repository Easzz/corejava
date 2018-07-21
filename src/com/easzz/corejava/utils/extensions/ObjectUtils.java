package com.easzz.corejava.utils.extensions;


import java.beans.PropertyDescriptor;
import java.util.function.BiConsumer;

/**
 * 对象工具类
 * Created by 李溪林 on 17-2-10.
 */
public class ObjectUtils {

    private static final String currentClassName = "ObjectUtils";

    /**
     * 将 source 对象种所有属性的值按照属性名称和类型定义的匹配关系复制到 target 对象中(会忽略转换异常)
     *
     * @param source    源对象
     * @param target    目标对象
     * @param <TSource> 源对象泛型类型
     * @param <TTarget> 目标对象泛型类型
     * @return 目标对象
     */
    public static <TSource, TTarget> TTarget copyPropertyTo(TSource source, TTarget target) {
        return copyPropertyTo(source, target, null, true);
    }

    /**
     * 将 source 对象种所有属性的值按照属性名称和类型定义的匹配关系复制到 target 对象中(会忽略转换异常)
     *
     * @param source    源对象
     * @param target    目标对象
     * @param consumer  额外的赋值表达式
     * @param <TSource> 源对象泛型类型
     * @param <TTarget> 目标对象泛型类型
     * @return 目标对象
     */
    public static <TSource, TTarget> TTarget copyPropertyTo(TSource source, TTarget target, BiConsumer<TSource, TTarget> consumer) {
        return copyPropertyTo(source, target, consumer, true);
    }

    /**
     * 将 source 对象种所有属性的值按照属性名称和类型定义的匹配关系复制到 target 对象中
     *
     * @param source      源对象
     * @param target      目标对象
     * @param ignoreError 是否忽略异常
     * @param <TSource> 源对象泛型类型
     * @param <TTarget> 目标对象泛型类型
     * @return 目标对象
     */
    public static <TSource, TTarget> TTarget copyPropertyTo(TSource source, TTarget target, boolean ignoreError) {
        return copyPropertyTo(source, target, null, ignoreError);
    }

    /**
     * 将 source 对象种所有属性的值按照属性名称和类型定义的匹配关系复制到 target 对象中
     *
     * @param source      源对象
     * @param target      目标对象
     * @param consumer    额外的赋值表达式
     * @param ignoreError 是否忽略异常
     * @param <TSource> 源对象泛型类型
     * @param <TTarget> 目标对象泛型类型
     * @return 目标对象
     */
    public static <TSource, TTarget> TTarget copyPropertyTo(TSource source, TTarget target, BiConsumer<TSource, TTarget> consumer, boolean ignoreError) {
        if (source == null) {
            return null;
        }
        checkObjectIsNotNull(target, "copyPropertyTo");
        if (source.getClass().equals(target.getClass())) {
            return (TTarget) source;
        }
        PropertyDescriptor[] sourceProps = TypeUtils.getAllowGetPropertyDescriptors(source.getClass());
        PropertyDescriptor[] targetProps = TypeUtils.getAllowSetPropertyDescriptors(target.getClass());
        for (PropertyDescriptor sp : sourceProps) {
            PropertyDescriptor tp = null;
            for (PropertyDescriptor item : targetProps) {
                if (sp.getName().equals(item.getName())) {
                    tp = item;
                    break;
                }
            }
            if (tp != null) {
                boolean match = false, getValue = false;
                Object value = null;
                Class<?> tpType = tp.getPropertyType(), spType = sp.getPropertyType();
                // 同类型
                if (tpType.equals(spType)) {
                    match = true;
                }
                // 小 from 大,如: List from ArrayList ,true
                if (!match && tpType.isAssignableFrom(spType)) {
                    match = true;
                }
                //可以相互指派(主要指基础类型和包装类型),如 boolean <==> Boolean
                if (!match && TypeUtils.isAssignable(tpType, spType)) {
                    if (spType.isPrimitive()) {
                        //如果 sp 为基础类型
                        match = true;
                    } else {
                        //如果 sp 为包装类型并且不为 null
                        try{
                            value = sp.getReadMethod().invoke(source);
                        }catch (Exception e){
                            throw new UtilsException(currentClassName + "中的方法[copyPropertyTo]发生异常:从源对象中获取[" + sp.getName() + "]属性值时发生错误.", e);
                        }
                        getValue = true;
                        if (value != null) {
                            match = true;
                        }
                    }
                }
                if (match) {
                    try {
                        tp.getWriteMethod().invoke(target, getValue ? value : sp.getReadMethod().invoke(source));
                    } catch (Exception e) {
                        if (!ignoreError) {
                            throw new UtilsException(currentClassName + "中的方法[copyPropertyTo]发生异常:将源对象[" + sp.getName() + "]属性值赋值到目标对象[" + tp.getName() + "]属性时发生错误.", e);
                        }
                    }
                }
            }
        }
        if (consumer != null) {
            try {
                consumer.accept(source, target);
            } catch (Exception e) {
                if (!ignoreError) {
                    throw new UtilsException(currentClassName + "中的方法[copyPropertyTo]发生异常:将源对象赋值到目标对象时执行lambda表达式发生错误.", e);
                }
            }
        }

        return target;
    }

    /**
     * 检查参数对象是否为null,为null则抛出异常
     *
     * @param methodName 调用方法名
     * @param <T>        数据类型
     */
    private static <T> void checkObjectIsNotNull(T obj, String methodName) {
        if (obj == null) {
            throw new UtilsException(currentClassName + "中的方法[" + methodName + "]发生异常:参数对象不能为null.");
        }
    }

}
