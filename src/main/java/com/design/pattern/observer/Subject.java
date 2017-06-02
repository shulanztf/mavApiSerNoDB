package com.design.pattern.observer;

/**
 * 
 * @Title: Subject
 * @Description:行为型模式:观察者模式（Observer）
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:41:40
 * @Version:1.0
 */
public interface Subject {
	/* 增加观察者 */
	public void add(Observer observer);

	/* 删除观察者 */
	public void del(Observer observer);

	/* 通知所有的观察者 */
	public void notifyObservers();

	/* 自身的操作 */
	public void operation();
}
