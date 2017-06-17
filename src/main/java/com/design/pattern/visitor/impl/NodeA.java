package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: NodeA
 * @Description:具体节点类NodeA
 * @Author: zhaotf
 * @Since:2017年6月5日 上午10:10:37
 * @Version:1.0
 */
public class NodeA extends Node {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * NodeA特有的方法
	 */
	public String operationA() {
		return "NodeA";
	}
}
