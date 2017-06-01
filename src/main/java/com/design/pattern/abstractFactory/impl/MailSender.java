package com.design.pattern.abstractFactory.impl;

import com.design.pattern.abstractFactory.Sender;

/**
 * 
 * @Title: MailSender
 * @Description:发送方实现类
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:44:28
 * @Version:1.0
 */
public class MailSender implements Sender {
	@Override
	public void Send() {
		System.out.println("this is 抽象工厂设计模式：MailSender");
	}
}
