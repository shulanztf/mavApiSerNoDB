package com.others.impl;

import com.others.Parent;

/**
 * 子類
 * @author zhaotf
 * 2017年6月4日 下午4:49:34
 * 
 */
public class Cliend extends Parent {
	
	public static void main(String[] args) {
		Cliend ccc = new Cliend();
		ccc.testMeth1();
		ccc.testMeth2();
		ccc.testMeth3();
	}

	@Override
	public Object testMeth2() {
		System.out.println("子类testMeth2");
		return null;
	}
	@Override
	public Object testMeth3() {
		System.out.println("子类testMeth3");
		return null;
	}

}
