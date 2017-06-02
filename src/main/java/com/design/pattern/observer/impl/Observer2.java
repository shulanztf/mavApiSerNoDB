package com.design.pattern.observer.impl;

import com.design.pattern.observer.Observer;

/**
 * 
 * @Title: Observer2
 * @Description:行为型模式:观察者模式（Observer）
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:41:04
 * @Version:1.0
 */
public class Observer2 implements Observer {

	@Override
	public void update() {
		System.out.println("observer2 has received!行为型模式:观察者模式（Observer） 222"); 
	}

}
