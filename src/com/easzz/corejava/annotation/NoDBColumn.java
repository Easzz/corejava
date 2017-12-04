package com.easzz.corejava.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Easzz on 07/07/2017.
 */
//用于属性
@Target(ElementType.FIELD)
public @interface NoDBColumn {

}
