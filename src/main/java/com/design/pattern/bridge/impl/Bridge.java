package com.design.pattern.bridge.impl;

import com.design.pattern.bridge.Sourceable;

/**
 * 
 * @Title: Bridge
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午6:05:37
 * @Version:1.0
 */
public abstract class Bridge {
	private Sourceable source;

	public void method() {
		source.method();
	}

	public Sourceable getSource() {
		return source;
	}

	public void setSource(Sourceable source) {
		this.source = source;
	}
}
