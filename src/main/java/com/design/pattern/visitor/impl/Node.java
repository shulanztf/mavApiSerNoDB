package com.design.pattern.visitor.impl;

import com.design.pattern.visitor.Visitor;

/**
 * 
 * @Title: Node
 * @Description:抽象节点类
 * @Author: zhaotf
 * @Since:2017年6月5日 上午10:10:13
 * @Version:1.0
 */
public abstract class Node {
	/**
	 * 接受操作
	 */
	public abstract void accept(Visitor visitor);
}
