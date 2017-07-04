package com.design.pattern.singleton;

/**
 * 
 * @Title: SingletonProxyTest
 * @Description:JAVA 反射对单例的影响
 * @Author: zhaotf
 * @Since:2017年7月4日 上午9:48:02
 * @Version:1.0
 */
public class SingletonProxyTest {
	private static SingletonProxyTest instance;

	public SingletonProxyTest() {
	}

	public static SingletonProxyTest getInstance() {
		if (instance == null) {
			synchronized (SingletonProxyTest.class) {
				if (instance == null) {
					instance = new SingletonProxyTest();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		try {
			SingletonProxyTest instance2 = SingletonProxyTest.getInstance();
			SingletonProxyTest instance3 = SingletonProxyTest.getInstance();
			SingletonProxyTest instance1 = SingletonProxyTest.class.newInstance();
			// System.out.println(instance1);
			// System.out.println(instance2);
			System.out.println(instance2 == instance1);
			System.out.println(instance2 == instance3);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
