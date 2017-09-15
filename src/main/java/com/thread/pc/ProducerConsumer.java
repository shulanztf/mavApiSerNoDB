package com.thread.pc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Title: ProducerConsumer
 * @Description:并发协作(生产者消费者模型)
 * @see http://www.cnblogs.com/linjiqin/p/3217050.html
 * @Author: zhaotf
 * @Since:2017年9月15日 下午3:40:06
 * @Version:1.0
 */
public class ProducerConsumer {

	public static void main(String[] args) throws InterruptedException {
		ProducerConsumer pc = new ProducerConsumer();
		Storage stor = pc.new Storage();// 仓库
		ExecutorService es = Executors.newCachedThreadPool();// 线程池
		es.submit(pc.new Consumer("消费者1", stor));
		// es.submit(pc.new Consumer("消费者2", stor));
		// es.submit(pc.new Consumer("消费者3", stor));
		// es.submit(pc.new Consumer("消费者4", stor));

		TimeUnit.SECONDS.sleep(2);

		es.submit(pc.new Producer("生产者1", stor));
		es.submit(pc.new Producer("生产者2", stor));
		es.submit(pc.new Producer("生产者3", stor));

	}

	/**
	 * 消费者
	 * 
	 */
	public class Consumer implements Runnable {
		private String name;
		private Storage stor = null;

		public Consumer(String name, Storage stor) {
			this.name = name;
			this.stor = stor;
		}

		@Override
		public void run() {
			try {
				while (true) {
					System.out.println(name + "准备消费产品.");
					Product product = stor.pop();
					System.out.println(name + "已消费(" + product.toString() + ").");
					System.out.println("===============");
					Thread.sleep(500);
					System.out.println(name + "唤醒");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 生产者
	 */
	public class Producer implements Runnable {
		private String name;
		private Storage stor = null;

		public Producer(String name, Storage s) {
			this.name = name;
			this.stor = s;
		}

		@Override
		public void run() {
			try {
				while (true) {
					Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
					System.out.println(name + "准备生产(" + product.toString() + ").");
					stor.push(product);
					System.out.println(name + "已生产(" + product.toString() + ").");
					System.out.println("===============");
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 仓库，用来存放产品
	 */
	public class Storage {
		BlockingQueue<Product> queues = new LinkedBlockingQueue<Product>(10);

		/**
		 * 生产
		 */
		public void push(Product p) throws InterruptedException {
			System.out.println("生产端，还有:" + queues.size());
			queues.put(p);
		}

		/**
		 * 消费
		 */
		public Product pop() throws InterruptedException {
			Product pro = queues.take();
			System.out.println("消费端，还有:" + queues.size() + "," + pro.id);
			return pro;
		}

	}

	/**
	 * 产品
	 */
	public class Product {
		private int id;

		public Product(int id) {
			this.id = id;
		}

		// 重写toString方法
		public String toString() {
			return "产品:" + this.id;
		}
	}
}
