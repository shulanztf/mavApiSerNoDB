package com.study.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.WritePendingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;

/**
 * 
 * @Title: XiaoNa
 * @Description:服务器代码
 * @see http://blog.csdn.net/zmx729618/article/details/53170867
 * @Author: zhaotf
 * @Since:2017年9月15日 上午7:39:13
 * @Version:1.0
 */
public class XiaoNaAioServer {
	private final AsynchronousServerSocketChannel server;
	/**
	 * 写队列，因为当前一个异步写调用还没完成之前，调用异步写会抛WritePendingException,所以需要一个写队列来缓存要写入的数据，
	 * 这是AIO比较坑的地方
	 */
	private final Queue<ByteBuffer> queue = new LinkedList<ByteBuffer>();
	private volatile boolean writing = false;// 写操作符，线程间可见，禁止重排序

	public XiaoNaAioServer() throws IOException {
		// 设置线程数为CPU核数
		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
				.withFixedThreadPool(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());
		server = AsynchronousServerSocketChannel.open(channelGroup);
		server.setOption(StandardSocketOptions.SO_REUSEADDR, true); // 重用端口
		server.bind(new InetSocketAddress(8383), 80); // 绑定端口并设置连接请求队列长度
	}

	/**
	 * 服务端监听
	 * 
	 * void
	 */
	public void listen() {
		System.out.println(Thread.currentThread().getName() + ": AIO服务端 方法:listen ");
		// 开始接受第一个连接请求
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel channel, Object attachment) {
				System.out.println(Thread.currentThread().getName() + ": AIO服务端 方法:completed ");
				server.accept(null, this); // 先安排处理下一个连接请求，异步非阻塞调用，所以不用担心挂住了;这里传入this是个地雷，小心多线程
				handle(channel); // 处理连接读写
			}

			private void handle(final AsynchronousSocketChannel channel) {
				System.out.println(Thread.currentThread().getName() + ": AIO服务端 方法:handle");
				// 每个AsynchronousSocketChannel，分配一个缓冲区
				final ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);
				readBuffer.clear();
				channel.read(readBuffer, null, new CompletionHandler<Integer, Object>() {

					@Override
					public void completed(Integer count, Object attachment) {
						System.out.println(Thread.currentThread().getName() + ": AIO服务端 方法:completed");

						if (count > 0) {
							try {
								readBuffer.flip();
								// CharBuffer charBuffer =
								// CharsetHelper.decode(readBuffer);
								CharBuffer charBuffer = Charset.forName("UTF-8").newDecoder().decode(readBuffer);
								String question = charBuffer.toString();
								String answer = XiaoNaAioHelper.getAnswer(question);

								// TODO
								// 写入也是异步调用，也可以使用传入CompletionHandler对象的方式来处理写入结果
								// channel.write(XiaoNoAioCharsetHelper.encode(CharBuffer.wrap(answer)));
								// try {
								// channel.write(
								// Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(answer)));
								// }
								// // 看来操作系统也不可靠
								// catch (WritePendingException wpe) {
								// // 休息一秒再重试，如果失败就不管了
								// // XiaoNaAioHelper.sleep(1); //
								// channel.write(
								// Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(answer)));
								// }

								writeStringMessage(channel, answer);

								readBuffer.clear();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							try {
								channel.close(); // 如果客户端关闭socket，那么服务器也需要关闭，否则浪费CPU
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						channel.read(readBuffer, null, this); // 异步调用OS处理下个读取请求,这里传入this是个地雷，小心多线程
					}

					/**
					 * 服务器读失败处理
					 * 
					 * @param exc
					 * @param attachment
					 */
					@Override
					public void failed(Throwable exc, Object attachment) {
						System.out.println("server read failed: " + exc);
						if (channel != null) {
							try {
								channel.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				});
			}

			/**
			 * 服务器接受连接失败处理
			 * 
			 * @param exc
			 * @param attachment
			 */
			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("server accept failed: " + exc);
			}

		});
	}

	/**
	 * 队列写的缓冲区的通道。调用是异步的，因此缓冲区在通过缓冲区后不安全。
	 * 
	 * @param buffer
	 *            发送到通道的缓冲区
	 */
	private void writeMessage(final AsynchronousSocketChannel channel, final ByteBuffer buffer) {
		boolean threadShouldWrite = false;

		synchronized (queue) {
			queue.add(buffer);
			if (!writing) {
				writing = true;
				threadShouldWrite = true;
			}
		}

		if (threadShouldWrite) {
			writeFromQueue(channel);
		}
	}

	/**
	 * 处理写出操作 队列
	 * 
	 * @param channel
	 *            void
	 */
	private void writeFromQueue(final AsynchronousSocketChannel channel) {
		ByteBuffer buffer;

		synchronized (queue) {
			buffer = queue.poll();// 获取并移除此队列
			if (buffer == null) {
				writing = false;
			}
		}

		// 写出数据
		if (writing) {
			writeBuffer(channel, buffer);
		}
	}

	/**
	 * 数据发送回客户端
	 * 
	 * @param channel
	 * @param buffer
	 *            void
	 */
	private void writeBuffer(final AsynchronousSocketChannel channel, ByteBuffer buffer) {
		channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				if (buffer.hasRemaining()) {
					channel.write(buffer, buffer, this); // 有数据时，写出操作
				} else {
					writeFromQueue(channel);// 回去检查一下是否有写入新的数据
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("AIO 服务端写出 失败:" + attachment + "," + exc);
			}
		});
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 * @throws CharacterCodingException
	 */
	private void writeStringMessage(final AsynchronousSocketChannel channel, String msg)
			throws CharacterCodingException {
		writeMessage(channel, Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(msg)));
	}

	public static void main(String[] args) throws IOException {
		final XiaoNaAioServer xiaona = new XiaoNaAioServer();

		new Thread("AIO服务端") {
			public void run() {
				try {
					xiaona.listen();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);// 异常退出
				}
			}
		}.start();

	}

}
