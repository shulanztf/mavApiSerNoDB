package com.design.pattern.bridge.impl;

import com.design.pattern.bridge.Sourceable;

/**
 * 
 * @Title: SourceSub2
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午6:04:55
 * @Version:1.0
 */
public class SourceSub2 implements Sourceable {

	@Override
	public void method() {
		System.out.println("this is the second sub!实现类SourceSub2");
	}

}
