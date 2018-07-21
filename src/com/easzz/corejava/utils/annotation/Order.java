package com.easzz.corejava.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排序号注解
 * 由于通过反射获取得类的属性描述信息集合是无序的,可通过本注解和 TypeUtils 来排序
 * Created by 李溪林 on 16-9-12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Order {

    /**
     * 排序号,默认值为 0.
     */
    int value() default 0;

}
