package com.design.pattern.mediator.impl;

import com.design.pattern.mediator.Mediator;

/**
 * 
 * @Title: MyMediator
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:34:03
 * @Version:1.0
 */
public class MyMediator implements Mediator {
	private User user1;
	private User user2;

	public User getUser1() {
		return user1;
	}

	public User getUser2() {
		return user2;
	}

	@Override
	public void createMediator() {
		user1 = new User1(this);
		user2 = new User2(this);
	}

	@Override
	public void workAll() {
		user1.work();
		user2.work();
	}

}
