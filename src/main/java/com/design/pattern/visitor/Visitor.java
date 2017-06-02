package com.design.pattern.visitor;

/**
 * 
 * @Title: Visitor
 * @Description:行为型模式:访问者模式（Visitor）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:57:46
 * @Version:1.0
 */
public interface Visitor {
	/**
	 * 
	 * @param sub
	 *            void
	 */
	public void visit(Subject sub);
}
