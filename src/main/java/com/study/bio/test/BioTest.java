package com.study.bio.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.study.bio.BioClient;
import com.study.bio.BioServerNormal;

/**
 * 
 * @Title: BioTest
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月13日 上午8:16:15
 * @Version:1.0
 */
public class BioTest {
	// 测试主方法
	public static void main(String[] args) throws InterruptedException {
		// 运行服务器
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// BioServerBetter.start();// 服务端源码__伪异步I/O
					BioServerNormal.start();// 同步阻塞模式
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		// 避免客户端先于服务器启动前执行代码
		// Thread.sleep(100);
		TimeUnit.SECONDS.sleep(3);

		// 运行客户端
		final char operators[] = { '+', '-', '*', '/' };
		final Random random = new Random(System.currentTimeMillis());
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				while (true) {
					// 随机产生算术表达式
					String expression = random.nextInt(10) + "" + operators[random.nextInt(4)]
							+ (random.nextInt(10) + 1);
					BioClient.send(expression);
					try {
						Thread.currentThread().sleep(random.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					// 随机产生算术表达式
					String expression = random.nextInt(10) + "" + operators[random.nextInt(4)]
							+ (random.nextInt(10) + 1);
					BioClient.send(expression);
					try {
						Thread.currentThread().sleep(random.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
