package com.thread.escape;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Title: EventSource
 * @Description:事件源
 * @see http://blog.csdn.net/u010001838/article/details/45691913
 * @Author: zhaotf
 * @Since:2017年9月15日 下午5:09:45
 * @Version:1.0
 */
public class EventSource<T> {
	private final List<T> eventListeners;

	public EventSource() {
		eventListeners = new ArrayList<T>();
	}

	public synchronized void registerListener(T eventListener) {
		this.eventListeners.add(eventListener);
		this.notifyAll();
	}

	public synchronized List<T> retrieveListeners() throws InterruptedException {
		List<T> dest = null;
		if (eventListeners.size() <= 0) {
			this.wait(); // 没有监听器注册到这里，就阻塞该线程。
		}
		dest = new ArrayList<T>(eventListeners.size());
		dest.addAll(eventListeners);
		return dest;
	}
}
