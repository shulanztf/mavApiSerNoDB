package com.study.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: ReadCompletionHandler
 * @Description:
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:13:07
 * @Version:1.0
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	private static final Logger logger = Logger.getLogger(ReadCompletionHandler.class);
	private AsynchronousSocketChannel socketChannel;

	public ReadCompletionHandler(AsynchronousSocketChannel socketChannel) {
		if (this.socketChannel == null) {
			this.socketChannel = socketChannel;
		}
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String request = new String(body, "UTF-8");
			logger.info("AIO服务端接收到的信息 : " + request);
			String currentTime = "AIO处理结果:" + Thread.currentThread().getName() + ":" + Thread.currentThread().getId()
					+ ":" + new Date().toString();
			doWrite(currentTime);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	}

	private void doWrite(String currentTime) {
		if (currentTime != null && currentTime.trim().length() > 0) {
			byte[] bytes = currentTime.getBytes();
			final ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			socketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					// 如果没有发送完继续发送
					if (attachment.hasRemaining()) {
						socketChannel.write(attachment, attachment, this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					try {
						socketChannel.close();
					} catch (IOException e) {
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
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
