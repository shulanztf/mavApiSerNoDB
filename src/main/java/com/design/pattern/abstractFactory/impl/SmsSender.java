package com.design.pattern.abstractFactory.impl;

import com.design.pattern.abstractFactory.Sender;

/**
 * 
 * @Title: SmsSender
 * @Description:发送方 实现类
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:59:50
 * @Version:1.0
 */
public class SmsSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is 抽象工厂设计模式：SmsSender");
	}

}
