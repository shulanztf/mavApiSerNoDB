package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: ChildReflect
 * @Description:
 * @Author: zhaotf
 * @Since:2017年7月14日 上午9:58:53
 * @Version:1.0
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ChildReflect  {

	/**
	 * 值得替换 导出是{a_id,b_id} 导入反过来,所以只用写一个
	 */
	public String[] ztfreplace() default {};

}
