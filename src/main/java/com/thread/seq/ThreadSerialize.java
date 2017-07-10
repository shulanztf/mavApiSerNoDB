package com.thread.seq;

/**
 * 
 * @Title: ThreadSerialize
 * @Description:java 线程串行化
 * @Author: zhaotf
 * @Since:2017年7月10日 上午9:34:38
 * @Version:1.0
 */
public class ThreadSerialize {

	public static void main(String[] args) {
		ThreadA threadA = new ThreadA();
		ThreadB threadB = new ThreadB();
		ThreadC threadC = new ThreadC();

		threadA.setThreadC(threadC);
		threadB.setThreadA(threadA);
		threadC.setThreadB(threadB);

		threadA.start();
		threadB.start();
		threadC.start();

//		for (int i = 0; i < 1; i++) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//		}

	}

}
