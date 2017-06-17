package com.others.impl;

/**
 * 
 * @author zhaotf
 * @since 2017年6月4日 下午4:54:08
 *
 */
public class Cliend1 extends Cliend {

	public static void main(String[] args) {
		Cliend ccc = new Cliend();
		ccc.testMeth1();
		ccc.testMeth2();
		ccc.testMeth3();

		Cliend1 c11 = new Cliend1();
		c11.testMeth3();
	}

	@Override
	public Object testMeth3() {
		System.out.println("再子类testMeth3");
		return null;
	}

}
