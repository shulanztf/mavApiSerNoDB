package com.study.async.concurrent.readwrite;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: CopyOnWriteMap
 * @Description:
 * @see http://ifeve.com/java-copy-on-write/
 * @author: zhaotf
 * @date: 2017年10月14日 下午4:23:54
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {
	private volatile Map<K, V> internalMap;

	public CopyOnWriteMap() {
		// TODO HashMap线程安全问题
		this.internalMap = new HashMap<K, V>();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		return this.internalMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		synchronized (this) {
			Map<K, V> map = new HashMap<K, V>(this.internalMap);
			V val = map.put(key, value);
			this.internalMap = map;
			return val;
		}
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized(this) {
			Map<K,V> map = new HashMap<K,V>(this.internalMap);
			map.putAll(internalMap);
			this.internalMap = map;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	// private volateile Map<K,V>

}
