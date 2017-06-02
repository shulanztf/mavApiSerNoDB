package com.design.pattern.iterator;

/**
 * 
 * @Title: Iterator
 * @Description:行为型模式:迭代子模式（Iterator）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午1:59:05
 * @Version:1.0
 */
public interface Iterator {

	/**
	 * 前移
	 * 
	 * @return Object
	 */
	public Object previous();

	/**
	 * 后移
	 * 
	 * @return Object
	 */
	public Object next();

	/**
	 * 是否有下一个
	 * 
	 * @return boolean
	 */
	public boolean hasNext();

	/**
	 * 取得第一个元素
	 * 
	 * @return Object
	 */
	public Object first();

}
