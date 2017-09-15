package com.study.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: AsyncTimeServerHandler
 * @Description:AIO服务端总线入口
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:11:16
 * @Version:1.0
 */
public class AsyncTimeServerHandler implements Runnable {
	private static final Logger logger = Logger.getLogger(AsyncTimeServerHandler.class);
	public CountDownLatch latch;
	public AsynchronousServerSocketChannel serverSocket;

	public AsyncTimeServerHandler(int port) {
		try {
			serverSocket = AsynchronousServerSocketChannel.open();
			serverSocket.bind(new InetSocketAddress(port)); // 绑定监听端口
			logger.info("AIO服务端启动:" + port);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@Override
	public void run() {
		// CountDownLatch作用是完成一组正在执行的操作之前，允许当前的线程一直阻塞，实际项目中不需要独立启动一个线程来处理的；多线程时，需要加锁
		latch = new CountDownLatch(1);
		serverSocket.accept(this, new AcceptCompletionHandler());// 等待连接，并注册CompletionHandler处理内核完成后的操作。
		try {
			logger.info("主线程:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
			latch.await();// 因为AIO不会阻塞调用进程，因此必须在主进程阻塞，才能保持进程存活。
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}

}
