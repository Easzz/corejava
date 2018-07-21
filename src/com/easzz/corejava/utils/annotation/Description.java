package com.easzz.corejava.utils.annotation;

import java.lang.annotation.*;

/**
 * 描述注解,用于表示描述信息
 * Created by 李溪林 on 16-9-12.
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    String value();
}
