package com.design.pattern.abstractFactory;

/**
 * 
 * @Title: Provider
 * @Description:抽象工厂模式（Abstract Factory） 消费方
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:48:15
 * @Version:1.0
 */
public interface Provider {
	public Sender produce();
}
