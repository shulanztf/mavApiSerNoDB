package com.study.nio;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @Title: Client
 * @Description:NIO客户端
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:29:29
 * @Version:1.0
 */
public class NioClient {

	public static void main(String[] args) {
		start();
		Scanner sca = new Scanner(System.in);
		System.out.println("NIO客户端测试开始……");

		ExecutorService es = Executors.newCachedThreadPool();
		while (!"quit".equals(sca.nextLine())) {
			for (int i = 0; i < 1; i++) {
				es.submit(new Runnable() {
					@Override
					public void run() {
						try {
							sendMsg("HHCF");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		}
		es.shutdown();
		sca.close();
		stop();
		System.out.println("NIO客户端测试结束……");
	}

	private static String DEFAULT_HOST = "127.0.0.1";
	private static int DEFAULT_PORT = 12345;
	private static NioClientHandle clientHandle;

	public static void start() {
		clientHandle = new NioClientHandle(DEFAULT_HOST, DEFAULT_PORT);
		new Thread(clientHandle, "NioClient" + System.currentTimeMillis()).start();
		System.out.println("NIO 客户端启动:" + DEFAULT_HOST + ":" + DEFAULT_PORT);
	}

	public static void stop() {
		if (clientHandle != null) {
			clientHandle.stop();
		}
	}

	// 向服务器发送消息
	public static boolean sendMsg(String msg) throws Exception {
		if (msg.equals("q")) {
			return false;
		}
		System.out.println(Thread.currentThread().getId() + ":NIO客户端发送消息内容:" + msg);
		clientHandle.sendMsg(msg);
		return true;
	}

}
