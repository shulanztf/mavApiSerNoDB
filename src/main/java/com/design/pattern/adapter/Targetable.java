package com.design.pattern.adapter;

/**
 * 
 * @Title: Targetable
 * @Description:结构型模式:适配器模式（Adapter）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午3:58:44
 * @Version:1.0
 */
public interface Targetable {
	/* 与原类中的方法相同 */
	public void method1();

	/* 新类的方法 */
	public void method2();
}
