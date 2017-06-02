package com.design.pattern.state;

/**
 * 
 * @Title: Context
 * @Description:行为型模式:状态模式（State）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:40:34
 * @Version:1.0
 */
public class Context {
	private State state;

	public Context(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void method() {
		if (state.getValue().equals("state1")) {
			state.method1();
		} else if (state.getValue().equals("state2")) {
			state.method2();
		}
	}

}
