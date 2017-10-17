package com.study.concurrent.collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.testng.log4testng.Logger;

/**
 * 
 * @Title: ConcurrentHashMapInstance
 * @Description:JAVA并发容器
 * @see http://blog.csdn.net/andy_gx/article/details/43496043
 * @Author: zhaotf
 * @Since:2017年10月13日 上午8:28:26
 * @Version:1.0
 */
public class ConcurrentHashMapInstance {
	private static Logger logger = Logger
			.getLogger(ConcurrentHashMapInstance.class);

	// 下面的代码也许能说明问题：运行了4个线程，每一次运行前打印lock的当前状态。运行后都要等待5秒钟。
	public static void main(String[] args) throws InterruptedException {
		final ExecutorService exec = Executors.newFixedThreadPool(4);
		final ReentrantLock lock = new ReentrantLock();
		final Condition con = lock.newCondition();
		final int time = 5;
		final Runnable add = new Runnable() {

			public void run() {
				System.out.println(Thread.currentThread().getId() + "-"
						+ System.currentTimeMillis() + ":Pre " + lock);
				lock.lock();
				try {
//					con.await(time, TimeUnit.SECONDS);
					 Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getId() + "-"
							+ System.currentTimeMillis() + ":Post "
							+ lock.toString());
					lock.unlock();
				}

			}

		};

		for (int index = 0; index < 4; index++) {
			exec.submit(add);
		}
		exec.shutdown();

	}
}
