package com.design.pattern.mediator.impl;

import com.design.pattern.mediator.Mediator;

/**
 * 
 * @Title: User2
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:35:51
 * @Version:1.0
 */
public class User2 extends User {

	public User2(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void work() {
		System.out.println("user2 exe!");
	}

}
