package com.easzz.corejava.annotation;

import java.lang.annotation.*;

/**
 * Created by Easzz on 07/07/2017.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     */
    enum Color {
        Blue, RED, GREEN
    }

    /**
     * 颜色属性
     *
     * @return
     */
    Color fruitColor() default Color.GREEN;
}
