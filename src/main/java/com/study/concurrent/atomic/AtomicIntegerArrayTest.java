package com.study.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @Title: AtomicIntegerArrayTest
 * @Description:
 * @see http://blog.csdn.net/sunxianghuang/article/details/52277370
 * @Author: zhaotf
 * @Since:2017年10月18日 下午4:33:29
 * @Version:1.0
 */
public class AtomicIntegerArrayTest {
	private static int threCount = 1000;
	private static CountDownLatch lanch = new CountDownLatch(threCount);
	static int[] values = new int[10];

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		for(int i=0;i<100;i++) {
			
		}
		
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < 10; j++) {
						values[j]++;
					}
				}
			}

		});
		th.start();

	}

}
