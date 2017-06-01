package com.design.pattern.adapter.impl;

import com.design.pattern.adapter.Targetable;

/**
 * 
 * @Title: Adapter
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午4:00:30
 * @Version:1.0
 */
public class Adapter extends Source implements Targetable {

	@Override
	public void method2() {
		System.out.println("this is the targetable method! method2");  
	}

}
