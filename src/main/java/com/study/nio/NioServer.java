package com.study.nio;

/**
 * 
 * @Title: NioServer
 * @Description:NIO创建的Server源码
 * @see http://blog.csdn.net/anxpp/article/details/51512200
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:25:22
 * @Version:1.0
 */
public class NioServer {
	private static int DEFAULT_PORT = 12345;
	private static NioServerHandle serverHandle;
	private static Object lock = new Object();

	public static void start() {
		start(DEFAULT_PORT);
	}

	public static void start(int port) {
		synchronized (lock) {
			if (serverHandle != null) {
				serverHandle.stop();
			}
			serverHandle = new NioServerHandle(port);
		}
		new Thread(serverHandle, "HmNioServer").start();
		System.out.println("NioServer 启动");
	}

}
