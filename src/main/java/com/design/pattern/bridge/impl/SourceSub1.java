package com.design.pattern.bridge.impl;

import com.design.pattern.bridge.Sourceable;

/**
 * 
 * @Title: SourceSub1
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午6:04:02
 * @Version:1.0
 */
public class SourceSub1 implements Sourceable {

	@Override
	public void method() {
		System.out.println("this is the first sub!实现类SourceSub1");  
	}

}
