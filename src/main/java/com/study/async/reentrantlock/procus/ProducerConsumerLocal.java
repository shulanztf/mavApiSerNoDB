package com.study.async.reentrantlock.procus;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @ClassName: ProducerConsumerLocal
 * @Description:使用Lock来实现生产者和消费者问题
 * @see http://blog.csdn.net/chenchaofuck1/article/details/51592429
 * @see http://blog.csdn.net/lingzhm/article/details/47864857
 * @author: zhaotf
 * @date: 2017年10月14日 下午2:50:44
 */
public class ProducerConsumerLocal {

	public static void main(String[] args) {
		Basket ba = new Basket();// 装馒头的篮子
		ExecutorService es = Executors.newFixedThreadPool(5);
		// 生产者
		for (int i = 0; i < 3; i++) {
			es.execute(new Product(ba));
		}
		// 消费者
		for (int i = 0; i < 3; i++) {
			es.execute(new Consumer(ba));
		}
		es.shutdown();
	}

}

/**
 * 商品类
 */
class ManTou {
	String id;

	public ManTou(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "MA:" + id;
	}
}

/**
 * @Description: 容器类
 */
class Basket {
	int max = 10;
	LinkedList<ManTou> list = new LinkedList<ManTou>();
	// Lock lock = new ReentrantLock();// 随机锁，并且创建两个condition，相当于两个阻塞队列
	Lock lock = new ReentrantLock(true);// 公平锁，并且创建两个condition，相当于两个阻塞队列
	Condition full = lock.newCondition(); // 对应-生产队列
	Condition empty = lock.newCondition(); // 对应-消费队列

	/**
	 * 放商品
	 */
	public void push(ManTou mt) {
		lock.lock();//
		try {
			while (max == list.size()) {
				System.out.println("容器已满-" + Thread.currentThread().getId());
				full.await();// 阻塞生产线程
			}
			list.add(mt);
			empty.signalAll();// 唤醒消费线程
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 取商品
	 */
	public ManTou pop() {
		ManTou mt = null;
		lock.lock();
		try {
			while (0 == list.size()) {
				System.out.println("容器为空-" + Thread.currentThread().getId());
				empty.await();// 阻塞消费线程
			}
			mt = list.removeFirst();
			full.signalAll();// 唤醒生产线程
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return mt;
	}

}

/**
 * @Description: 生产者
 */
class Product implements Runnable {
	Basket basket;

	public Product(Basket basket) {
		this.basket = basket;
	}

	@Override
	public void run() {
		while (true) {
			ManTou mt = new ManTou(Thread.currentThread().getId() + ":"
					+ System.currentTimeMillis());
			this.basket.push(mt);
			System.out.println(Thread.currentThread().getId() + ":生产了:" + mt);
			try {
				Thread.sleep((long) (Math.random() * 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

/**
 * @Description: 消费者
 */
class Consumer implements Runnable {
	Basket basket;

	public Consumer(Basket basket) {
		this.basket = basket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep((long) (Math.random() * 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ManTou mt = this.basket.pop();
			System.out.println(Thread.currentThread().getId() + ":消费了:" + mt);
		}
	}

}
