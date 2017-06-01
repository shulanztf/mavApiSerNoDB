package com.design.pattern.proxy.impl;

import com.design.pattern.proxy.Sourceable;

/**
 * 
 * @Title: Proxy
 * @Description:代理执行类
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:20:39
 * @Version:1.0
 */
public class Proxy implements Sourceable {
	private Source source;

	public Proxy() {
		super();
		this.source = new Source();
	}

	private void atfer() {
		System.out.println("after proxy!代理执行类");
	}

	private void before() {
		System.out.println("before proxy!代理执行类");
	}

	@Override
	public void method() {
		before();
		source.method();
		atfer();
	}

}
