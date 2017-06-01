package com.design.pattern.abstractFactory.impl;

import com.design.pattern.abstractFactory.Provider;
import com.design.pattern.abstractFactory.Sender;

/**
 * 
 * @Title: SendSmsFactory
 * @Description:工厂 实现类
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:49:33
 * @Version:1.0
 */
public class SendSmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new SmsSender();
	}

}
