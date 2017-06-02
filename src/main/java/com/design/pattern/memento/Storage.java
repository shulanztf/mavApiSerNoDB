package com.design.pattern.memento;

/**
 * 
 * @Title: Storage
 * @Description:行为型模式:备忘录模式（Memento）
 * @see Storage类是存储备忘录的类，持有Memento类的实例
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:18:58
 * @Version:1.0
 */
public class Storage {
	private Memento memento;

	public Storage(Memento memento) {
		this.memento = memento;
	}

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}

}
