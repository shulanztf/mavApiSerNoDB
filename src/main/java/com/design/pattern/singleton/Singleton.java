package com.design.pattern.singleton;

/**
 * 
 * @Title: Singleton
 * @Description:创建型模式:单例模式（Singleton）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午2:37:49
 * @Version:1.0
 */
public class Singleton {
	/* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
	private static Singleton instance = null;

	/* 私有构造方法，防止被实例化 */
	private Singleton() {
	}

	/* 静态工程方法，创建实例 */
	public static Singleton getInstance() {
		// synchronized 只对第一次创建时加锁，提升性能
		synchronized (instance) {
			if (instance == null) {
				instance = new Singleton();
			}
		}
		return instance;
	}

	/* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return instance;
	}
}
