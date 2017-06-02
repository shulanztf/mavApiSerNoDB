package com.design.pattern.observer.impl;

import java.util.Enumeration;
import java.util.Vector;

import com.design.pattern.observer.Observer;
import com.design.pattern.observer.Subject;

/**
 * 
 * @Title: AbstractSubject
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:42:06
 * @Version:1.0
 */
public abstract class AbstractSubject implements Subject {
	private Vector<Observer> vector = new Vector<Observer>();

	@Override
	public void add(Observer observer) {
		vector.add(observer);
	}

	@Override
	public void del(Observer observer) {
		vector.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Enumeration<Observer> enumo = vector.elements();
		while (enumo.hasMoreElements()) {
			enumo.nextElement().update();
		}
	}

}
