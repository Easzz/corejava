package com.easzz.corejava.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Easzz on 07/07/2017.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	String name() default "filedName";

	String setFuncName() default "setFieldName";

	String getFuncName() default "getFieldName";

	boolean defaultDBValue() default false;
}
