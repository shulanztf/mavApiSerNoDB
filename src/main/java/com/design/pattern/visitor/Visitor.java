package com.design.pattern.visitor;

import com.design.pattern.visitor.impl.NodeA;
import com.design.pattern.visitor.impl.NodeB;

/**
 * 
 * @Title: Visitor
 * @Description:行为型模式:访问者模式（Visitor）
 * @see http://www.jb51.net/article/63281.htm
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:57:46
 * @Version:1.0
 */
public interface Visitor {
	/**
	 * 对应于Subject的访问操作
	 * 
	 * @param sub
	 *            void
	 */
	public void visit(Subject sub);

	/**
	 * 对应于NodeA的访问操作
	 */
	public void visit(NodeA node);

	/**
	 * 对应于NodeB的访问操作
	 */
	public void visit(NodeB node);

}
