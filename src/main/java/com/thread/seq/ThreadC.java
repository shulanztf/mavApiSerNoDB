package com.thread.seq;

/**
 * 
 * @Title: ThreadC
 * @Description:java 线程串行化
 * @Author: zhaotf
 * @Since:2017年7月10日 上午9:36:57
 * @Version:1.0
 */
public class ThreadC extends Thread {
	private ThreadB threadB;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (threadB) {
				synchronized (this) {
					System.out.println("I am ThreadC..." + i);
					this.notify();
				}
				try {
					threadB.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void setThreadB(ThreadB threadB) {
		this.threadB = threadB;
	}
}
