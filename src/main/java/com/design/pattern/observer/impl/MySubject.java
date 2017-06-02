package com.design.pattern.observer.impl;

/**
 * 
 * @Title: MySubject
 * @Description:行为型模式:观察者模式（Observer）
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:42:58
 * @Version:1.0
 */
public class MySubject extends AbstractSubject {

	@Override
	public void operation() {
		System.out.println("update self!行为型模式:观察者模式（Observer）");
		notifyObservers();
	}

}
