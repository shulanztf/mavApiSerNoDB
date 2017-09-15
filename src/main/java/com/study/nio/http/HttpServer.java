package com.study.nio.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 
 * @Title: HttpServer
 * @Description:
 * @see https://www.coderknock.com/blog/2016/04/16/NioSocketHTTP.html
 * @Author: zhaotf
 * @Since:2017年9月15日 下午2:44:17
 * @Version:1.0
 */
public class HttpServer {
	public static void main(String[] args) {
		/**
		 * 启动监听 当监听到请求时根据SelectionKey的操作类型交给内部类Handler进行处理
		 */
		try {
			// 创建ServerSocketChannel
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 设置监听8080端口
			ssc.socket().bind(new InetSocketAddress(8080));
			// 设置为非阻塞模式
			ssc.configureBlocking(false);
			// 为ServerSocketChannel注册Selector
			Selector selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				// 等待请求,每次等待阻塞3s,超过3秒后线程继续运行,如果传入0或使用无参重载方法,将一直阻塞
				if (selector.select(3000) == 0) {
					System.out.println("等待请求超时~~~~~");
					continue;
				}
				System.out.println("处理请求~~~~~");
				// 获取等待处理的SelectionKey
				Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
				while (keyIterator.hasNext()) {
					SelectionKey key = keyIterator.next();
					// 启动新线程处理SelectionKey
					new Thread(new HttpHandler(key)).run();
					// 处理完成删除请求
					keyIterator.remove();
					System.out.println("删除");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

	}

	/**
	 * 请求处理类
	 */
	private static class HttpHandler implements Runnable {
		private int bufferSize = 1024;
		private String localCharset = "UTF-8";
		private SelectionKey key;

		public HttpHandler(SelectionKey key) {
			this.key = key;
		}

		/**
		 * 处理请求操作
		 *
		 * @param key
		 * @throws IOException
		 */
		public void handleAccept() throws IOException {
			// 获取Channel
			SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
			// 设置非阻塞
			sc.configureBlocking(false);
			// 注册读操作的Selector
			sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
		}

		/**
		 * 处理读操作
		 *
		 * @param key
		 * @throws IOException
		 */
		public void handleRead() throws IOException {
			// 获取Channel
			SocketChannel sc = ((SocketChannel) key.channel());
			// 获取ByteBuffer
			ByteBuffer buffer = (ByteBuffer) key.attachment();
			// 重置ByteBuffer。设置limit=capacity、position=0、mark=-1
			buffer.clear();
			// 没有获取到内容则关闭
			if (sc.read(buffer) == -1) {
				sc.close();
			} else {
				buffer.flip();
				// 返回数据到客户端
				String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
				// 控制台打印请求报文头
				String[] requestMessage = receivedString.split("\r\n");
				for (String s : requestMessage) {
					System.out.println(s);
					// 遇到空行说明报文头信息已经打印完
					if (s.isEmpty()) {
						break;
					}
				}
				// 控制台打印首行信息
				String[] firstLine = requestMessage[0].split(" ");
				System.out.println();
				System.out.println("Method:\t" + firstLine[0]);
				System.out.println("url:\t" + firstLine[1]);
				System.out.println("HTTP Version:\t" + firstLine[2]);
				System.out.println();
				// 返回客户端
				StringBuilder sendString = new StringBuilder();
				// 响应报文首行
				sendString.append("HTTP/1.1 200 OK\r\n");
				sendString.append("Content-Type:text/html;charset=" + localCharset + "\r\n");
				// 响应报文头最后加一个空行
				sendString.append("\r\n");
				sendString.append("<!DOCTYPE html><html lang=\"en\"><head>");
				sendString.append("<meta charset=\"UTF-8\">");
				sendString.append("<title>显示报文</title>");
				sendString.append("</head>");
				sendString.append("<body>");
				sendString.append("接收到请求的报文是:<br/>");
				for (String s : requestMessage) {
					sendString.append(s + "<br/>");
				}
				sendString.append("</body></html>");
				buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
				sc.write(buffer);
				// 关闭SocketChannel
				sc.close();
			}

		}

		@Override
		public void run() {
			try {
				if (key.isAcceptable()) {
					handleAccept();
				}
				if (key.isReadable()) {
					handleRead();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
