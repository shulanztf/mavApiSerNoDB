package com.design.pattern.adapter.impl;

import com.design.pattern.adapter.Targetable;

/**
 * 
 * @Title: Wrapper
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午4:07:07
 * @Version:1.0
 */
public class Wrapper implements Targetable {
	private Source source;

	public Wrapper() {
	}

	public Wrapper(Source source) {
		super();
		this.source = source;
	}

	@Override
	public void method1() {
		this.source.method1();
	}

	@Override
	public void method2() {
		System.out.println("this is the targetable method!");
	}

	public static void main(String[] args) {
		Source source = new Source();
		Targetable target = new Wrapper(source);
		target.method1();
		target.method2();
	}

}
