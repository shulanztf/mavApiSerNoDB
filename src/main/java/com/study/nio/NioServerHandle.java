package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.study.nio.util.CalculatorUtils;

/**
 * 
 * @Title: NioServerHandle
 * @Description:NIO服务端
 * @see http://blog.csdn.net/anxpp/article/details/51512200
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:26:32
 * @Version:1.0
 */
public class NioServerHandle implements Runnable {
	private Selector selector;
	private ServerSocketChannel serverChannel;
	private volatile boolean started;

	/**
	 * 构造方法
	 * 
	 * @param port
	 *            指定要监听的端口号
	 */
	public NioServerHandle(int port) {
		try {
			selector = Selector.open(); // 创建选择器
			serverChannel = ServerSocketChannel.open(); // 打开监听通道
			serverChannel.configureBlocking(false);// 开启非阻塞模式,如果为true，则此通道将被置于阻塞模式；如果为false，则此通道将被置于非阻塞模式
			serverChannel.socket().bind(new InetSocketAddress(port), 1024); // 绑定端口backlog设为1024
			serverChannel.register(selector, SelectionKey.OP_ACCEPT); // 监听客户端连接请求
			started = true; // 标记服务器已开启
			System.out.println("服务器已启动，端口号：" + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		started = false;
	}

	@Override
	public void run() {
		// 注册线程/事件轮询
		SelectionKey key = null;
		// 循环遍历selector
		while (started) {
			try {
				selector.select(1000); // 无论是否有读写事件发生，selector每隔1s被唤醒一次
				// selector.select(); // 阻塞,只有当至少一个注册的事件发生的时候才会继续.
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while (it.hasNext()) {
					key = it.next();
					it.remove();// 移除已执行线程，及事件
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		// selector关闭后会自动释放里面管理的资源
		if (selector != null)
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * 事件/线程状态转换，并，业务处理
	 * 
	 * @param key
	 * @throws IOException
	 *             void
	 * @throws InterruptedException
	 */
	private void handleInput(SelectionKey key) throws IOException, InterruptedException {
		if (key.isValid()) {
			// 处理新接入的请求消息
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				// 通过ServerSocketChannel的accept创建SocketChannel实例
				// 完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false); // 设置为非阻塞的
				sc.register(selector, SelectionKey.OP_READ); // 注册为读
			}

			// 读取消息
			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer bb = ByteBuffer.allocate(1024); // 创建ByteBuffer，并开辟一个1M的缓冲区
				int readBytes = sc.read(bb); // 读取请求码流，返回读取到的字节数
				// 读取到字节，对字节进行编解码
				if (readBytes > 0) {
					bb.flip(); // 将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
					byte[] bytes = new byte[bb.remaining()]; // 根据缓冲区可读字节数创建字节数组
					bb.get(bytes); // 将缓冲区可读字节数组复制到新建的数组中
					String msg = new String(bytes, "UTF-8");
					System.out.println("NIO服务器收到消息:" + Thread.currentThread().getId() + ":" + msg);
					// 处理数据
					String rslt = null;
					try {
						// rslt = CalculatorUtils.cal(msg).toString();
						rslt = "处理后台=" + msg;
					} catch (Exception e) {
						rslt = "计算错误：" + e.getMessage();
						System.out.println("服务端异常发生:" + Thread.currentThread().getId() + ":" + rslt);
						TimeUnit.SECONDS.sleep(3);// 出现异常，暂停5秒
					}
					System.out.println("服务器处理结果:" + Thread.currentThread().getId() + ":" + rslt);
					// 发送应答消息
					doWrite(sc, rslt);
				} else if (readBytes == 0) {
					// 没有读取到字节 忽略
				} else if (readBytes < 0) {
					// 链路已经关闭，释放资源
					key.cancel();// 取消此键的通道到其选择器的注册。
					sc.close();
				}
			}
		}
	}

	// 异步发送应答消息
	private void doWrite(SocketChannel channel, String response) throws IOException {
		byte[] bytes = response.getBytes(); // 将消息编码为字节数组
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length); // 根据数组容量创建ByteBuffer
		writeBuffer.put(bytes); // 将字节数组复制到缓冲区
		writeBuffer.flip(); // flip操作
		channel.write(writeBuffer); // 发送缓冲区的字节数组
		// ****此处不含处理“写半包”的代码
	}

}
