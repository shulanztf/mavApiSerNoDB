package com.study.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: AcceptCompletionHandler
 * @Description:AIO服务端，连接接收器
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:12:01
 * @Version:1.0
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
	private static final Logger logger = Logger.getLogger(AcceptCompletionHandler.class);

	/**
	 * @param result
	 *            客户端管道/socket
	 * @param attachment
	 *            服务端通道/socket
	 */
	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		// 再次让asynchronousServerSocketChannel对象调用accept方法是因为：
		// 调用AsynchronousServerSocketChannel的accept方法后，如果有新的客户端接入，
		// 系统将回调我们传入的CompletionHandler实例的completed方法，表示新客户端连接成功。
		// 因为AsynchronousServerSocketChannel可以接受成千上万个客户端，所以需要继续调用它的accept方法，接受其他客户端连接，最终形成一个环；
		// 每当一个客户端连接成功后，再异步接受新的客户端连接
		attachment.serverSocket.accept(attachment, this);
		ByteBuffer readBuffer = ByteBuffer.allocate(1024);
		result.read(readBuffer, readBuffer, new ReadCompletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		attachment.latch.countDown();// 结束主线程，停止程序
		logger.error(exc);
	}

}
