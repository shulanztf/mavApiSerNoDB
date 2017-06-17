package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Subject;
import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: VisitorA
 * @Description:具体访问者VisitorA类
 * @see http://www.jb51.net/article/63281.htm
 * @Author: zhaotf
 * @Since:2017年6月5日 上午10:08:28
 * @Version:1.0
 */
public class VisitorA implements Visitor {

	@Override
	public void visit(Subject sub) {
	}

	@Override
	public void visit(NodeA node) {
		System.out.println("访问者模式探索：" + node.operationA());
	}

	@Override
	public void visit(NodeB node) {
		System.out.println(node.operationB());
	}

}
