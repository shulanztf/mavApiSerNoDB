package com.design.pattern.facade;

/**
 * 
 * @Title: CPU
 * @Description:结构型模式:外观模式（Facade）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:46:31
 * @Version:1.0
 */
public class CPU {
	public void startup() {
		System.out.println("cpu startup!");
	}

	public void shutdown() {
		System.out.println("cpu shutdown!");
	}
}
