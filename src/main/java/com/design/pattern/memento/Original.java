package com.design.pattern.memento;

/**
 * 
 * @Title: Original
 * @Description:行为型模式:备忘录模式（Memento）
 * @see Original类是原始类，里面有需要保存的属性value及创建一个备忘录类，用来保存value值。
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:17:50
 * @Version:1.0
 */
public class Original {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Original(String value) {
		this.value = value;
	}

	public Memento createMemento() {
		return new Memento(value);
	}

	public void restoreMemento(Memento memento) {
		this.value = memento.getValue();
	}
}
