package com.design.pattern.singleton;

import java.util.Vector;

/**
 * 
 * @Title: SingletonTest
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午2:44:31
 * @Version:1.0
 */
public class SingletonTest {
	private static SingletonTest instance = null;
	private Vector<?> properties = null;

	public Vector<?> getProperties() {
		return properties;
	}

	private SingletonTest() {
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new SingletonTest();
		}
	}

	public static SingletonTest getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	public void updateProperties() {
		SingletonTest shadow = new SingletonTest();
		properties = shadow.getProperties();
	}
}
