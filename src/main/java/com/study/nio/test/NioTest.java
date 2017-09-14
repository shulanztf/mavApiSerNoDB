package com.study.nio.test;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.study.nio.NioClient;
import com.study.nio.NioServer;

/**
 * 
 * @Title: NioTest
 * @Description:测试方法
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:32:34
 * @Version:1.0
 */
public class NioTest {

	// 测试主方法
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 运行服务器
		NioServer.start();

		// 避免客户端先于服务器启动前执行代码
		// Thread.sleep(100);
		TimeUnit.SECONDS.sleep(2);

		// 运行客户端
		NioClient.start();

		// 发送消息
//		new Thread("th1") {
//			@Override
//			public void run() {
//				try {
//					while (true) {
//						NioClient.sendMsg("线程1:" + Thread.currentThread().getName() + ":"
//								+ Thread.currentThread().getId() + ":" + System.currentTimeMillis());
//						TimeUnit.SECONDS.sleep(1);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					return;
//				}
//			}
//		}.start();
//		new Thread("th2") {
//			@Override
//			public void run() {
//				try {
//					while (true) {
//						NioClient.sendMsg("线程2:" + Thread.currentThread().getName() + ":"
//								+ Thread.currentThread().getId() + ":" + System.currentTimeMillis());
//						TimeUnit.SECONDS.sleep(1);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					return;
//				}
//			}
//		}.start();

		while (NioClient.sendMsg(new Scanner(System.in).nextLine())) {
		}

	}

}
