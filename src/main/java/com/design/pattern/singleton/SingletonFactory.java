package com.design.pattern.singleton;

/**
 * 
 * @Title: SingletonFactory
 * @Description:单例工厂类
 * @Author: zhaotf
 * @Since:2017年6月1日 下午2:41:51
 * @Version:1.0
 */
public class SingletonFactory {
	private static Singleton instance;

	public static Singleton getInstance() {
		return SingletonFactory.instance;
	}
}
