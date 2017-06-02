package com.design.pattern.memento;

/**
 * 
 * @Title: Memento
 * @Description:行为型模式:备忘录模式（Memento）
 * @see Memento类是备忘录类
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:18:38
 * @Version:1.0
 */
public class Memento {
	private String value;

	public Memento(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
