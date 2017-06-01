package com.design.pattern.decorator.impl;

import com.design.pattern.decorator.Sourceable;

/**
 * 
 * @Title: Decorator
 * @Description:结构型模式:装饰模式（Decorator）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:04:31
 * @Version:1.0
 */
public class Decorator implements Sourceable {
	private Sourceable source;

	public Decorator(Sourceable source) {
		super();
		this.source = source;
	}

	@Override
	public void method() {
		System.out.println("before decorator!");
		source.method();
		System.out.println("after decorator!");
	}

}
