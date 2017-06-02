package com.design.pattern.bridge.impl;

/**
 * 
 * @Title: MyBridge
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午6:06:11
 * @Version:1.0
 */
public class MyBridge extends Bridge {
	public void method() {
		getSource().method();
	}
}
