package com.mult.thre.sxt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: MyContainer
 * @Description: 多线程学习
 * @see 使用JAVA 多线程 api
 * @see 创建容器，线程1添加元素，线程2监测，加到5个时，线程2退出
 * @see 性能、精确度加强版
 * @author zhaotf
 * @date 2017年9月9日 下午4:35:53
 */
public class MyContainer2 {

	volatile private List<Object> list = new ArrayList<Object>();

	public void add(Object obj) {
		this.list.add(obj);
	}

	public int size() {
		return this.list.size();
	}

	public static void main(String[] args) {
		final MyContainer2 con = new MyContainer2();
		final CountDownLatch latch = new CountDownLatch(1);

		// 监控，到点自动退出
		new Thread("t2") {
			public void run() {
				System.out.println("t2 start");
				if (con.size() < 5) {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2 end:" + con.size());
			}
		}.start();

		// try {
		// TimeUnit.SECONDS.sleep(1);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// 添加元素
		new Thread("t1") {
			public void run() {
				System.out.println("t1 start");
				for (int i = 0; i < 10; i++) {
					con.list.add("object" + i);
					System.out.println("list size:" + con.list.size());

					if (con.list.size() == 5) {
						latch.countDown();
						try {
							latch.await(1, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// try {
					// TimeUnit.SECONDS.sleep(1);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
				System.out.println("t1 end");
			}
		}.start();
	}

	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// System.out.println("当前数量:" + this.size());
	// }

}
