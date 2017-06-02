package com.design.pattern.mediator.impl;

import com.design.pattern.mediator.Mediator;

/**
 * 
 * @Title: User
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:34:44
 * @Version:1.0
 */
public abstract class User {
	private Mediator mediator;

	public Mediator getMediator() {
		return mediator;
	}

	public User(Mediator mediator) {
		this.mediator = mediator;
	}

	public abstract void work();
}
