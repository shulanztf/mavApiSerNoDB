package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: Reflect
 * @Description: JAVA 自定义注解
 * @author zhaotf
 * @date 2017年7月9日 下午2:57:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reflect {
	String name() default "sunguoli";
}
