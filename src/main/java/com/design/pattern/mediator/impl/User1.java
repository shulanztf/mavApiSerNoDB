package com.design.pattern.mediator.impl;

import com.design.pattern.mediator.Mediator;

/**
 * 
 * @Title: User1
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:35:20
 * @Version:1.0
 */
public class User1 extends User {

	public User1(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void work() {
//		this.getMediator()
		System.out.println("user1 exe!");
	}

}
