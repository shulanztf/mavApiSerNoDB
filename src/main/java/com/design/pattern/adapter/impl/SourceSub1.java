package com.design.pattern.adapter.impl;

/**
 * 
 * @Title: SourceSub1
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午4:52:31
 * @Version:1.0
 */
public class SourceSub1 extends Wrapper2 {

	@Override
	public void method1() {
		System.out.println("the sourceable interface's first Sub1!");
	}

}
