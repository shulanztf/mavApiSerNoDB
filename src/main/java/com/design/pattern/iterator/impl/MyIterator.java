package com.design.pattern.iterator.impl;

import com.design.pattern.iterator.Collection;
import com.design.pattern.iterator.Iterator;

/**
 * 
 * @Title: MyIterator
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:00:41
 * @Version:1.0
 */
public class MyIterator implements Iterator {
	private Collection collection;
	private int pos = -1;

	public MyIterator() {
	}

	public MyIterator(Collection collection) {
		this.collection = collection;
	}

	@Override
	public Object previous() {
		if (pos > 0) {
			pos--;
		}
		return collection.get(pos);
	}

	@Override
	public Object next() {
		if (pos < collection.size() - 1) {
			pos++;
		}
		return collection.get(pos);
	}

	@Override
	public boolean hasNext() {
		if (pos < collection.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object first() {
		pos = 0;
		return collection.get(pos);
	}

}
