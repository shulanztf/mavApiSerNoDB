package com.study.nio;

/**
 * 
 * @Title: Client
 * @Description:NIO客户端
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:29:29
 * @Version:1.0
 */
public class NioClient {
	private static String DEFAULT_HOST = "127.0.0.1";
	private static int DEFAULT_PORT = 12345;
	private static NioClientHandle clientHandle;
	private static Object lock = new Object();

	public static void start() {
		start(DEFAULT_HOST, DEFAULT_PORT);
	}

	public static void start(String ip, int port) {
		synchronized (lock) {
			if (clientHandle != null) {
				clientHandle.stop();
			}
			clientHandle = new NioClientHandle(ip, port);
		}
		new Thread(clientHandle, "Server").start();
	}

	// 向服务器发送消息
	public static boolean sendMsg(String msg) throws Exception {
		if (msg.equals("q"))
			return false;
		clientHandle.sendMsg(msg);
		return true;
	}

	public static void main(String[] args) {
		start();
	}
}
