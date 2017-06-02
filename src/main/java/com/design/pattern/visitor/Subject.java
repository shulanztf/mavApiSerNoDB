package com.design.pattern.visitor;

/**
 * 
 * @Title: Subject
 * @Description:行为型模式:访问者模式（Visitor）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午3:59:58
 * @Version:1.0
 */
public interface Subject {
	/**
	 * 
	 * @param visitor
	 *            void
	 */
	public void accept(Visitor visitor);

	/**
	 * 
	 * @return String
	 */
	public String getSubject();
}
