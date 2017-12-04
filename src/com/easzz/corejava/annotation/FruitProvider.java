package com.easzz.corejava.annotation;

import java.lang.annotation.*;

/**
 * Created by Easzz on 07/07/2017.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {
	int id() default -1;

	String name() default "";

	String address() default "";

}
