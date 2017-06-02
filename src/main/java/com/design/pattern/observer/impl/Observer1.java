package com.design.pattern.observer.impl;

import com.design.pattern.observer.Observer;

/**
 * 
 * @Title: Observer1
 * @Description:行为型模式:观察者模式（Observer）
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:40:00
 * @Version:1.0
 */
public class Observer1 implements Observer {

	@Override
	public void update() {
		System.out.println("observer1 has received!行为型模式:观察者模式（Observer） 111");
	}

}
