package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Subject;
import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: VisitorB
 * @Description:具体访问者VisitorB类
 * @Author: zhaotf
 * @Since:2017年6月5日 上午10:09:27
 * @Version:1.0
 */
public class VisitorB implements Visitor {

	@Override
	public void visit(Subject sub) {
	}

	@Override
	public void visit(NodeA node) {
		System.out.println(node.operationA());
	}

	@Override
	public void visit(NodeB node) {
		System.out.println("访问者模式探索：" + node.operationB());
	}

}
