package com.design.pattern.singleton;

/**
 * 
 * @Title: Counter
 * @Description:
 * @Author: zhaotf
 * @Since:2017年7月4日 上午10:13:04
 * @Version:1.0
 */
public class Counter implements Runnable {
	private int count;

	public Counter() {
		this.count = 0;
	}

	public static void main(String[] args) {
		Counter c1 = new Counter();
		// Counter c2 = new Counter();
		Thread t1 = new Thread(c1);
		Thread t2 = new Thread(c1);
		Thread t3 = new Thread(c1);
		Thread t4 = new Thread(c1);
		t1.setName("A");
		t2.setName("B");
		t3.setName("C");
		t4.setName("D");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName);
		if (threadName.equals("A")) {
			countAdd();
		} else if (threadName.equals("B")) {
			printCount();
		} else if (threadName.equals("C")) {
			printCount();
		} else if (threadName.equals("D")) {
			printCount();
		}
	}

	private void printCount() {
//		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				try {
					System.out.println(Thread.currentThread().getName() + "未加锁 count:" + (count++));
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//		}
	}

	private void countAdd() {
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				try {
					System.out.println(Thread.currentThread().getName() + "加锁 count:" + (count++));
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
