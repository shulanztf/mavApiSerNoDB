package com.study.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @Title: AtomicDemo
 * @Description:JAVA原子操作
 * @see
 * @Author: zhaotf
 * @Since:2017年10月18日 下午4:00:05
 * @Version:1.0
 */
public class AtomicDemo implements Runnable {
	private static final AtomicInteger atomInt = new AtomicInteger();

	@Override
	public void run() {
		atomInt.incrementAndGet();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// AtomicLong al = new AtomicLong(System.currentTimeMillis());
		// for (int i = 0; i < 100; i++) {
		// // System.out.println("cc:" + al.getAndIncrement());
		// System.out.println(System.currentTimeMillis() + ":cc:" +
		// al.getAndAdd(System.currentTimeMillis()));
		// }

		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			es.execute(new Runnable() {
				AtomicLong atom = new AtomicLong(Thread.currentThread().getId() + System.currentTimeMillis());

				@Override
				public void run() {
					for (int i = 0; i < 10; i++) {
						System.out.println(Thread.currentThread().getId() + ":tt:" + atom.getAndIncrement());
					}
				}
			});
		}

		es.shutdown();
		// AtomicDemo demo = new AtomicDemo();
		// for (int i = 0; i < 1000; i++) {
		// Thread th = new Thread(demo);
		// th.start();
		// try {
		// th.join();// 等待该线程终止。
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// System.out.println("原子操作最终结果:" + atomInt);
	}

}
