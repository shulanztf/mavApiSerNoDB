package com.study.async.reentrantlock.procus.clust2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @ClassName: BlockingQueueTest2Local
 * @Description:
 * @see http://www.cnblogs.com/linjiqin/archive/2013/05/30/3108188.html
 * @author: zhaotf
 * @date: 2017年10月14日 下午7:31:03
 */
public class BlockingQueueTest2Local {

	public static void main(String[] args) {
		BlockingQueueTest2Local test = new BlockingQueueTest2Local();
		Basket basket = test.new Basket();
		ExecutorService es = Executors.newCachedThreadPool();
		// 生产者
		for (int i = 0; i < 3; i++) {
			es.submit(test.new Producer(basket));
		}
		// 消费者
		for (int i = 0; i < 3; i++) {
			es.submit(test.new Consumer(basket));
		}

		es.shutdown();
	}

	/**
	 * @Description: 定义装苹果的篮子
	 */
	public class Basket {
		BlockingQueue<String> list = new LinkedBlockingQueue<String>(10);

		/**
		 * 生产
		 * 
		 * @throws InterruptedException
		 */
		public void produce(String key) throws InterruptedException {
			// put方法放入一个苹果，若basket满了，等到basket有位置
			list.put(key);
		}

		/**
		 * 消费
		 * 
		 * @throws InterruptedException
		 */
		public String consume() throws InterruptedException {
			// take方法取出一个苹果，若basket为空，等到basket有苹果为止(获取并移除此队列的头部)
			return list.take();
		}

	}

	/**
	 * 生产者
	 */
	public class Producer implements Runnable {
		private Basket basket;

		public Producer(Basket basket) {
			this.basket = basket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					String key = Thread.currentThread().getId() + ":生产了:"
							+ System.currentTimeMillis();
					basket.produce(key);
					System.out.println(key);
					Thread.sleep((long) (Math.random() * 2000));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @Description:消费者
	 */
	class Consumer implements Runnable {
		private Basket basket;

		public Consumer(Basket basket) {
			this.basket = basket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					System.out.println(Thread.currentThread().getId() + ":消费了:"
							+ this.basket.consume());
					Thread.sleep((long) (Math.random() * 2000));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
