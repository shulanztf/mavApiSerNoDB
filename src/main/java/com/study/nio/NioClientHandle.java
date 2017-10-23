package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @Title: NioClientHandle
 * @Description:NIO客户端
 * @Author: zhaotf
 * @Since:2017年9月13日 下午4:30:58
 * @Version:1.0
 */
public class NioClientHandle implements Runnable {
	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean started;

	public NioClientHandle(String ip, int port) {
		this.host = ip;
		this.port = port;
		try {
			selector = Selector.open(); // 创建选择器
			socketChannel = SocketChannel.open(); // 打开监听通道
			socketChannel.configureBlocking(false);// 开启非阻塞模式,如果为true，则此通道将被置于阻塞模式；如果为false，则此通道将被置于非阻塞模式
			started = true;
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
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// 循环遍历selector
		Set<SelectionKey> keys = null;
		while (started) {
			try {
				selector.select(1000); // 无论是否有读写事件发生，selector每隔1s被唤醒一次
				// selector.select(); // 阻塞,只有当至少一个注册的事件发生的时候才会继续.
				keys = selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();// 切记移除,不移除的后果是本次的就绪的key集合下次会再次返回,导致无限循环，CPU消耗100%
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
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		// selector关闭后会自动释放里面管理的资源
		if (selector != null) {
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			SocketChannel sc = (SocketChannel) key.channel();
			// 通道已完成其套接字连接操作，并且， 未连接此通道的套接字时
			if (key.isConnectable() && !sc.finishConnect()) {
				System.exit(1);// 异常终止
			}
			// 读取消息
			if (key.isReadable()) {
				ByteBuffer buffer = ByteBuffer.allocate(1024); // 创建ByteBuffer，并开辟一个1M的缓冲区
				int readBytes = sc.read(buffer); // 读取请求码流，返回读取到的字节数
				// 读取到字节，对字节进行编解码
				if (readBytes > 0) {
					buffer.flip(); // 将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
					byte[] bytes = new byte[buffer.remaining()]; // 根据缓冲区可读字节数创建字节数组
					buffer.get(bytes); // 将缓冲区可读字节数组复制到新建的数组中
					String result = new String(bytes, "UTF-8");
					System.out.println(Thread.currentThread().getId() + ":NIO客户端收到消息:" + result);
				} else if (readBytes == 0) {
					// 没有读取到字节 忽略
				} else if (readBytes < 0) {
					// 链路已经关闭，释放资源
					key.cancel();
					sc.close();
				}
			}
		}
	}

	/**
	 * 异步发送消息
	 * 
	 * @param channel
	 * @param msg
	 * @throws IOException
	 */
	private void doWrite(SocketChannel channel, String msg) throws IOException {
		byte[] bytes = msg.getBytes(); // 将消息编码为字节数组
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length); // 根据数组容量创建ByteBuffer
		writeBuffer.put(bytes); // 将字节数组复制到缓冲区
		writeBuffer.flip(); // flip操作
		channel.write(writeBuffer); // 发送缓冲区的字节数组
		// ****此处不含处理“写半包”的代码
	}

	/**
	 * 连接服务端
	 * 
	 * @throws IOException
	 *             void
	 */
	private void doConnect() throws IOException {
		if (!socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);// 线程/事件，注册为连接状态
		}
	}

	public void sendMsg(String msg) throws Exception {
		socketChannel.register(selector, SelectionKey.OP_READ);// 线程/事件，注册为读取状态
		doWrite(socketChannel, msg);
	}

}
