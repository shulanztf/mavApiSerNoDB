package com.design.pattern.decorator.impl;

import com.design.pattern.decorator.Sourceable;

/**
 * 
 * @Title: Source
 * @Description:结构型模式:装饰模式（Decorator）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:03:46
 * @Version:1.0
 */
public class Source implements Sourceable {

	@Override
	public void method() {
		System.out.println("the original method!结构型模式:装饰模式（Decorator）");
	}

}
