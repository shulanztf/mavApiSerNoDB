package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Subject;
import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: MySubject
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:00:51
 * @Version:1.0
 */
public class MySubject implements Subject {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getSubject() {
		return "love";
	}

}
