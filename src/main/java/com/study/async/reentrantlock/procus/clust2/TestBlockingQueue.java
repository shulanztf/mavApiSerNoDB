package com.study.async.reentrantlock.procus.clust2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @ClassName: TestBlockingQueue
 * @Description:
 * @see http://blog.csdn.net/done58/article/details/50996282
 * @author: zhaotf
 * @date: 2017年10月14日 下午7:21:01
 */
public class TestBlockingQueue {
	protected static BlockingQueue<Hamburger> queue = new LinkedBlockingQueue<Hamburger>(
			10);

	public static void main(String[] args) throws InterruptedException {
		Producer p1 = new Producer();
		Producer p2 = new Producer();
		Consumer c1 = new Consumer();
		Consumer c2 = new Consumer();
		p1.start();
		p2.start();
		c1.start();
		c2.start();
		System.out.println("Thread.sleep(1000)");
		Thread.sleep(1000);
		c1.interrupt();// 中断线程。
	}
}

class Hamburger {
	int id;

	public Hamburger(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Hamburger: " + id;
	}
}

class Producer extends Thread {
	@Override
	public void run() {
		int i = 0;
		while (i < 10) {
			Hamburger e = new Hamburger(i);
			try {
				System.out.println("Produce Hamburger: " + i);
				TestBlockingQueue.queue.put(e);
			} catch (InterruptedException e1) {
				System.out.println("Hamburger so many, it was closed.");
				return;
			}
			i++;
		}
	}
}

class Consumer extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Eat Hamburger: "
						+ TestBlockingQueue.queue.take());
			} catch (InterruptedException e1) {
				System.out.println("Hamburger so less, It was stopped.");
				return;
			}
		}
	}
}
