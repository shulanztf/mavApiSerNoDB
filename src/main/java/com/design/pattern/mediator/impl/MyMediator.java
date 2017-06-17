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
public class MyMediator extends Mediator {

	@Override
	public void createMediator() {
		this.setUser1(new User1(this));
		this.setUser2(new User2(this));
	}

	@Override
	public void workAll() {
		this.getUser1().work();
		this.getUser2().work();
	}

}
