package com.study.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: AsyncHhcfClientHandler
 * @Description:
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月15日 上午11:09:38
 * @Version:1.0
 */
public class AsyncHhcfClientHandler implements CompletionHandler<Integer, ByteBuffer>, Runnable {
	private static final Logger logger = Logger.getLogger(AsyncHhcfClientHandler.class);
	private AsynchronousSocketChannel socketChannel;
	private String host;
	private int port;
	/** 线程门闩 */
	private CountDownLatch latch;

	public AsyncHhcfClientHandler(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		socketChannel = AsynchronousSocketChannel.open();
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		socketChannel.connect(new InetSocketAddress(host, port));
		try {
			latch.await();// 因为AIO异常非阻塞，需要主线程等待
			socketChannel.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		byte[] req = ("队列时间排列" + Thread.currentThread().getName()).getBytes();
		ByteBuffer bf = ByteBuffer.allocate(req.length);
		bf.put(req);
		bf.flip();

		if (attachment.hasRemaining()) {
			socketChannel.write(bf, bf, this);
		} else {
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			
			
			
		}

	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			logger.error(exc);
			socketChannel.close();
			latch.countDown();
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
