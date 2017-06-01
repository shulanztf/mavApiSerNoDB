package com.design.pattern.proxy.impl;

import com.design.pattern.proxy.Sourceable;

/**
 * 
 * @Title: Source
 * @Description:结构型模式:代理模式（Proxy）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:19:41
 * @Version:1.0
 */
public class Source implements Sourceable {
	@Override
	public void method() {
		System.out.println("the original method!结构型模式:代理模式（Proxy），被代理类");
	}
}
