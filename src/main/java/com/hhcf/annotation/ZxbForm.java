package com.hhcf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: ZxbForm
 * @Description:
 * @see http://blog.csdn.net/linuu/article/details/51065476
 * @Author: zhaotf
 * @Since:2017年9月30日 下午3:20:33
 * @Version:1.0
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZxbForm {
	String value() default "";
}
