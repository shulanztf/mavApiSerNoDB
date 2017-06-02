package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Subject;
import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: MyVisitor
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:58:43
 * @Version:1.0
 */
public class MyVisitor implements Visitor {

	@Override
	public void visit(Subject sub) {
		System.out.println("visit the subject：" + sub.getSubject());
	}

}
