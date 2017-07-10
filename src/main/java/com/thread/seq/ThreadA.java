package com.thread.seq;

/**
 * 
 * @Title: ThreadA
 * @Description:java 线程串行化
 * @Author: zhaotf
 * @Since:2017年7月10日 上午9:35:24
 * @Version:1.0
 */
public class ThreadA extends Thread {
	private ThreadC threadC;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (threadC) {
				synchronized (this) {
					System.out.println("I am ThreadA..." + i);
					this.notify();
				}
				try {
					threadC.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void setThreadC(ThreadC threadC) {
		this.threadC = threadC;
	}
}
