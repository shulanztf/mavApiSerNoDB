package com.study.nio.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @ClassName: EchoServer
 * @Description:
 * @see http://www.jianshu.com/p/1123c9164e3e
 * @author: zhaotf
 * @date: 2017年10月21日 下午4:31:52
 */
public class EchoServer {
	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap boot = new ServerBootstrap(); // (2)
			boot.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					// (3)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
								@Override
								public void initChannel(SocketChannel ch)
										throws Exception {
									ch.pipeline().addLast(
									// new LoggingHandler(LogLevel.INFO),
											new EchoServerHandler());
								}
							});

			// Start the server.
			ChannelFuture cf = boot.bind(port).sync(); // (5)

			// Wait until the server socket is closed.
			cf.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new EchoServer(port).run();
	}

}
