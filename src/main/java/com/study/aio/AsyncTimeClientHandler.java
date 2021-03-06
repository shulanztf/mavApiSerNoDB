package com.study.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: AsyncTimeClientHandler
 * @Description:AIO服务端总线入口
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:15:16
 * @Version:1.0
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {
	private static final Logger logger = Logger.getLogger(AsyncTimeClientHandler.class);
	private AsynchronousSocketChannel socketChannel;
	private String host;
	private int port;
	/** 线程门闩 */
	private CountDownLatch latch;

	public AsyncTimeClientHandler(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		socketChannel = AsynchronousSocketChannel.open();
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		socketChannel.connect(new InetSocketAddress(host, port), this, this);
		try {
			latch.await();// 因为AIO异常非阻塞，需要主线程等待
			socketChannel.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		byte[] req = ("队列时间排列" + Thread.currentThread().getName()).getBytes();
		ByteBuffer bf = ByteBuffer.allocate(req.length);
		bf.put(req);
		bf.flip();
		socketChannel.write(bf, bf, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if (attachment.hasRemaining()) {
					// 发送信息
					socketChannel.write(attachment, attachment, this);
				} else {
					// 接收服务端返回信息
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					socketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

						@Override
						public void completed(Integer result, ByteBuffer attachment) {
							attachment.flip();
							byte[] bytes = new byte[attachment.remaining()];
							attachment.get(bytes);
							try {
								String body = new String(bytes, "UTF-8");
								logger.info("AIO客户端，接收服务端返回信息:" + body);
								latch.countDown();// 释放线程
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								logger.error(e);
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							try {
								logger.error(exc);
								socketChannel.close();
								latch.countDown();
							} catch (IOException e) {
								e.printStackTrace();
								logger.error(e);
							}
						}
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					socketChannel.close();
					latch.countDown();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		exc.printStackTrace();
		try {
			socketChannel.close();
			latch.countDown();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

}
