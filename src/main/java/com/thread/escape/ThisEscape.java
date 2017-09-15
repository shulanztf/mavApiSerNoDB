package com.thread.escape;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @Title: ThisEscape
 * @Description:模拟this逃逸的类
 * @see http://blog.csdn.net/u010001838/article/details/45691913
 * @Author: zhaotf
 * @Since:2017年9月15日 下午5:10:26
 * @Version:1.0
 */
public class ThisEscape {
	public final int id;
	public final String name;

	public ThisEscape(EventSource<AEventListener> eventSource) {
		id = 1;
		eventSource.registerListener(new AEventListener() {

			@Override
			public void onEvent(Object object) {
				System.out.println("id:" + ThisEscape.this.id);
				System.out.println("name:" + ThisEscape.this.name);
			}

		});

		try {
			TimeUnit.SECONDS.sleep(1);// 此处加入相关断点。
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		name = "thisEscape test.";
	}

	public static void main(String[] args) {
		EventSource<AEventListener> eventSource = new EventSource<AEventListener>();
		ListenerRunnable listenerRunnable = new ListenerRunnable(eventSource);
		new Thread(listenerRunnable).start();
		System.out.println();
		ThisEscape escape = new ThisEscape(eventSource);
		System.out.println(escape);
	}

}
