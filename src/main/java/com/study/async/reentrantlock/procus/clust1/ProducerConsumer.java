package com.study.async.reentrantlock.procus.clust1;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @ClassName: ProducerConsumer
 * @Description:使用Lock来实现生产者和消费者问题
 * @see http://blog.csdn.net/lingzhm/article/details/47864857
 * @author: zhaotf
 * @date: 2017年10月14日 下午2:28:35
 */
public class ProducerConsumer {

	public static void main(String[] args) {
		Basket ba = new Basket();// 装馒头的篮子
		Product p1 = new Product(ba);
		Product p2 = new Product(ba);
		Product p3 = new Product(ba);
		Product p4 = new Product(ba);
		Consumer c1 = new Consumer(ba);
		Consumer c2 = new Consumer(ba);
		Consumer c3 = new Consumer(ba);
		new Thread(p1).start();
		new Thread(p2).start();
		new Thread(p3).start();
		new Thread(p4).start();

		new Thread(c1).start();
		new Thread(c2).start();
		new Thread(c3).start();
	}

}

// 馒头
class ManTou {
	int id;

	public ManTou(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ManTou" + id;
	}
}

// 装馒头的篮子
class Basket {
	int max = 6;
	LinkedList<ManTou> manTous = new LinkedList<ManTou>();
	Lock lock = new ReentrantLock(); // 锁对象
	Condition full = lock.newCondition(); // 用来监控篮子是否满的Condition实例
	Condition empty = lock.newCondition(); // 用来监控篮子是否空的Condition实例

	// 往篮子里面放馒头
	public void push(ManTou m) {
		lock.lock();
		try {
			while (max == manTous.size()) {
				System.out.println("篮子是满的，待会儿再生产..."
						+ Thread.currentThread().getId());
				full.await();
			}
			manTous.add(m);
			empty.signal();// 唤醒一个等待线程。
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	// 往篮子里面取馒头
	public ManTou pop() {
		ManTou m = null;
		lock.lock();
		try {
			while (manTous.size() == 0) {
				System.out.println("篮子是空的，待会儿再吃..."
						+ Thread.currentThread().getId());
				empty.await();
			}
			m = manTous.removeFirst();
			full.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return m;
	}
}

// 生产者
class Product implements Runnable {
	Basket basket;

	public Product(Basket basket) {
		this.basket = basket;
	}

	public void run() {
		for (int i = 0; i < 40; i++) {
			ManTou m = new ManTou(i);
			basket.push(m);
			System.out.println(Thread.currentThread().getId() + ":生产了:" + m);
			try {
				Thread.sleep((int) (Math.random() * 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}

// 消费者
class Consumer implements Runnable {
	Basket basket;

	public Consumer(Basket basket) {
		this.basket = basket;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep((int) (Math.random() * 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ManTou m = basket.pop();
			System.out.println(Thread.currentThread().getId() + ":消费了:" + m);
		}
	}
}
