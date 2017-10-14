package com.study.async.reentrantlock.procus.clust2;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @ClassName: ConcurrentLinkedQueueTestLocal
 * @Description:
 * @see http://blog.csdn.net/done58/article/details/50996282
 * @author: zhaotf
 * @date: 2017年10月14日 下午8:03:27
 */
public class ConcurrentLinkedQueueTestLocal {
	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	private CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		ConcurrentLinkedQueueTestLocal test = new ConcurrentLinkedQueueTestLocal();
		ExecutorService es = Executors.newCachedThreadPool();
		// 生产
		for (int i = 0; i < 3; i++) {
			es.submit(test.new Producer());
		}
		// 消费
		for (int i = 0; i < 5; i++) {
			es.submit(test.new Consumer());
		}
		es.shutdown();
	}

	/**
	 * @Description: 生产
	 */
	class Producer implements Runnable {

		@Override
		public void run() {
			while (true) {
				String key = Thread.currentThread().getId() + ":生产了:"
						+ System.currentTimeMillis();
				ConcurrentLinkedQueueTestLocal.queue.offer(key);
				latch.countDown();
				System.out.println(key);
				try {
					Thread.sleep((long) (Math.random() * 3000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @Description: 消费
	 */
	class Consumer implements Runnable {

		@Override
		public void run() {
			while (true) {

				if (ConcurrentLinkedQueueTestLocal.queue.isEmpty()) {
					try {
						System.out.println(Thread.currentThread().getId()
								+ ":等待中");
						latch.await();
						continue;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println(Thread.currentThread().getId() + "=消费了="
							+ ConcurrentLinkedQueueTestLocal.queue.poll());
					try {
						Thread.sleep((long) (Math.random() * 2000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
