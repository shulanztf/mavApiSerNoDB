package com.thread.escape;

import java.util.List;

/**
 * 
 * @Title: ListenerRunnable
 * @Description:监听器线程
 * @see http://blog.csdn.net/u010001838/article/details/45691913
 * @Author: zhaotf
 * @Since:2017年9月15日 下午5:08:52
 * @Version:1.0
 */
public class ListenerRunnable implements Runnable {
	private EventSource<AEventListener> source;

	public ListenerRunnable(EventSource<AEventListener> source) {
		this.source = source;
	}

	@Override
	public void run() {
        List<AEventListener> listeners = null;  
        try {  
            listeners = source.retrieveListeners();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
  
        for (AEventListener eventListener : listeners) {  
            eventListener.onEvent(new Object());  
        } 
	}

}
