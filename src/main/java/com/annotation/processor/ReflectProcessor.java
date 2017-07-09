package com.annotation.processor;

import java.lang.reflect.Method;

import com.annotation.Reflect;

/**
 * 
 * @ClassName: ReflectProcessor
 * @Description: TODO
 * @author zhaotf
 * @date 2017年7月9日 下午3:03:14
 */
public class ReflectProcessor {

	public void parseMethod(final Class<?> clazz) throws Exception {
		final Object obj = clazz.getConstructor(new Class[] {}).newInstance(
				new Object[] {});
		final Method[] methods = clazz.getDeclaredMethods();
		for (final Method method : methods) {
//			System.out.println(method.getAnnotations().getClass());
			final Reflect my = method.getAnnotation(Reflect.class);
			if (null != my) {
//				System.out.println(method.getName());
				method.invoke(obj, my.name());
			}
		}
	}

}
