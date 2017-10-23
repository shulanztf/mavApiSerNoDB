package com.study.nio;

import java.util.Scanner;

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

	/**
	 * 启动服务，JAVA线程池方式
	 * 
	 * void
	 */
	public static void start() {
		serverHandle = new NioServerHandle(DEFAULT_PORT);
		new Thread(serverHandle, "HmNioServer").start();
		System.out.println("NioServer 启动……");
	}

	/**
	 * 停止服务
	 * 
	 * void
	 */
	public static void stop() {
		if (serverHandle != null) {
			serverHandle.stop();
		}
	}

	public static void main(String[] args) {
		start();
		System.out.println("NIO服务端测试开始……");
		Scanner sca = new Scanner(System.in);
		while (!"quit".equals(sca.nextLine())) {
		}
		stop();
		sca.close();
		System.out.println("NIO服务端退出……");
	}

}
