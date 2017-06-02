package com.design.pattern.chain.responsibility.impl;

import com.design.pattern.chain.responsibility.Handler;

/**
 * 
 * @Title: MyHandler
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:27:05
 * @Version:1.0
 */
public class MyHandler extends AbstractHandler implements Handler {
	private String name;

	public MyHandler(String name) {
		this.name = name;
	}

	@Override
	public void operator() {
		System.out.println(name + "开始!");
		if (getHandler() != null) {
			getHandler().operator();
		}
		System.out.println(name + "结束!");
	}

}
