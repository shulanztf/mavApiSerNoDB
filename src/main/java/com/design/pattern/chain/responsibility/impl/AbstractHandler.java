package com.design.pattern.chain.responsibility.impl;

import com.design.pattern.chain.responsibility.Handler;

/**
 * 
 * @Title: AbstractHandler
 * @Description:行为型模式:责任链模式（Chain of Responsibility）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:26:23
 * @Version:1.0
 */
public abstract class AbstractHandler {
	private Handler handler;

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
