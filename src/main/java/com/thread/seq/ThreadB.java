package com.thread.seq;

/**
 * 
 * @Title: ThreadB
 * @Description:java 线程串行化
 * @Author: zhaotf
 * @Since:2017年7月10日 上午9:37:03
 * @Version:1.0
 */
public class ThreadB extends Thread {
	private ThreadA threadA;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (threadA) {
				synchronized (this) {
					System.out.println("I am ThreadB..." + i);
					this.notify();
				}
				try {
					threadA.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void setThreadA(ThreadA threadA) {
		this.threadA = threadA;
	}
}
