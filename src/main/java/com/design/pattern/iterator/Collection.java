package com.design.pattern.iterator;

/**
 * 
 * @Title: Collection
 * @Description:行为型模式:迭代子模式（Iterator）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午1:58:20
 * @Version:1.0
 */
public interface Collection {

	/**
	 * 取得集合
	 * 
	 * @return Iterator
	 */
	public Iterator iterator();

	/**
	 * 取得集合元素
	 */
	public Object get(int i);

	/**
	 * 取得集合大小
	 */
	public int size();

}
