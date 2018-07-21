package com.easzz.corejava.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 小数精度注解
 * Created by 李溪林 on 17-11-05.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Precision {

    /**
     * 小数精度,默认值为 0.
     */
    int value() default 0;

}
