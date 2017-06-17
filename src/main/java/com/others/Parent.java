package com.others;

/**
 * 父类
 * @author zhaotf
 * 2017年6月4日 下午4:41:49
 */
public abstract class Parent {
	
	public Object testMeth1() {
		System.out.println("父类testMeth1");
		return null;
	}

	
	public Object testMeth2() {
		System.out.println("父类testMeth1");
		return null;
	}
	
	public abstract Object testMeth3();
}
