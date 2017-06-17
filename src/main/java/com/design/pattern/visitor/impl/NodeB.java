package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: NodeB
 * @Description:具体节点类NodeB
 * @Author: zhaotf
 * @Since:2017年6月5日 上午10:11:38
 * @Version:1.0
 */
public class NodeB extends Node {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * NodeB特有的方法
	 */
	public String operationB() {
		return "NodeB";
	}

}
