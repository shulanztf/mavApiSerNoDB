package com.design.pattern.state;

/**
 * 
 * @Title: State
 * @Description:行为型模式:状态模式（State）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:38:38
 * @Version:1.0
 */
public class State {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void method1() {
		System.out.println("execute the first opt!状态1");
	}

	public void method2() {
		System.out.println("execute the second opt!状态2");
	}
}
