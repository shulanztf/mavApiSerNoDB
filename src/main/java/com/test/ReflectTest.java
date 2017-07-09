package com.test;

import com.annotation.Reflect;
import com.annotation.processor.ReflectProcessor;

/**
 * @ClassName: ReflectTest
 * @Description: TODO JAVA 自定义注解
 * @author zhaotf
 * @date 2017年7月9日 下午3:04:32
 */
public class ReflectTest {

	@Reflect
	public static void sayHello(final String name) {
		System.out.println("==>> Hi, " + name + " [sayHello]");
	}

	@Reflect(name = "abcccc")
	public static void sayHelloToSomeone(final String name) {
		System.out.println("==>> Hi, " + name + " [sayHelloToSomeone]");
	}

	public static void main(final String[] args) throws Exception {
		final ReflectProcessor relectProcessor = new ReflectProcessor();
		relectProcessor.parseMethod(ReflectTest.class);
	}

}
