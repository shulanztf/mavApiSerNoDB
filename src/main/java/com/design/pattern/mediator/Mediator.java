package com.design.pattern.mediator;

import com.design.pattern.mediator.impl.User;

/**
 * 
 * @Title: Mediator
 * @Description:行为型模式:中介者模式（Mediator）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:32:56
 * @Version:1.0
 */
public abstract class Mediator {
	private User user1;
	private User user2;

	public User getUser1() {
		return user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public abstract void createMediator();

	public abstract void workAll();
}
