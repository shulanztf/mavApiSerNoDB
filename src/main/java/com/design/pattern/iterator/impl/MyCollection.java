package com.design.pattern.iterator.impl;

import com.design.pattern.iterator.Collection;
import com.design.pattern.iterator.Iterator;

/**
 * 
 * @Title: MyCollection
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午1:59:35
 * @Version:1.0
 */
public class MyCollection implements Collection {
	public String string[] = { "A", "B", "C", "D", "E" };

	@Override
	public Iterator iterator() {
		return new MyIterator(this);
	}

	@Override
	public Object get(int i) {
		return string[i];
	}

	@Override
	public int size() {
		return string.length;
	}

}
