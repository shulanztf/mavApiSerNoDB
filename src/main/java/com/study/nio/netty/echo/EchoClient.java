package com.study.nio.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @ClassName: EchoClient
 * @Description:
 * @see http://www.jianshu.com/p/1123c9164e3e
 * @author: zhaotf
 * @date: 2017年10月21日 下午7:02:55
 */
public class EchoClient {

	private final String host;
	private final int port;
	private final int firstMessageSize;

	public EchoClient(String host, int port, int firstMessageSize) {
		this.host = host;
		this.port = port;
		this.firstMessageSize = firstMessageSize;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
							// new LoggingHandler(LogLevel.INFO),
									new EchoClientHandler(firstMessageSize));
						}
					});

			// 启动客户端.
			ChannelFuture cf = boot.connect(host, port).sync();

			// 等待连接关闭。
			cf.channel().closeFuture().sync();
		} finally {
			// 终止所有线程。
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 8080;
		int firstMessageSize = 256;
		new EchoClient(host, port, firstMessageSize).run();
	}

}
