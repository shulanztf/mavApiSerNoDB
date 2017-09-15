package com.study.nio.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: NioHttpServer
 * @Description:
 * @see http://blog.csdn.net/maosijunzi/article/details/37658899
 * @Author: zhaotf
 * @Since:2017年9月15日 下午2:40:10
 * @Version:1.0
 */
public class NioHttpServer {
	private static Logger logger = Logger.getLogger(NioHttpServer.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		NioHttpServer server = new NioHttpServer();
		server.start();
		// 运行代码，然后打开浏览器在地址栏输入：http://localhost:8084/hello?aa=1&bb=2,
		// 回车后浏览器显示hello world 。而后台则输出http请求头信息：如下所示：

	}

	public void start() throws Exception {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(8084));
		while (true) {
			while (selector.select() > 0) {
				Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();
					if (key.isAcceptable()) {
						ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
						SocketChannel channel = ssc.accept();
						if (channel != null) {
							channel.configureBlocking(false);
							channel.register(selector, SelectionKey.OP_READ);// 客户socket通道注册读操作
						}
					} else if (key.isReadable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						channel.configureBlocking(false);
						String receive = receive(channel);
						BufferedReader b = new BufferedReader(new StringReader(receive));

						String s = b.readLine();
						while (s != null) {
							System.out.println(s);
							s = b.readLine();
						}
						b.close();
						channel.register(selector, SelectionKey.OP_WRITE);
					} else if (key.isWritable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						String hello = "hello world...";
						ByteBuffer buffer = ByteBuffer.allocate(1024);

						byte[] bytes = hello.getBytes();
						buffer.put(bytes);
						buffer.flip();
						channel.write(buffer);
						channel.shutdownInput();
						channel.close();
					}
				}
			}
		}
	}

	// 接受数据
	private String receive(SocketChannel socketChannel) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] bytes = null;
		int size = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((size = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
		}
		bytes = baos.toByteArray();

		return new String(bytes);
	}
}
