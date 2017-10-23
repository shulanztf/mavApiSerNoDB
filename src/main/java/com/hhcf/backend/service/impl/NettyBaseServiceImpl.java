package com.hhcf.backend.service.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhcf.backend.service.NettyBaseService;

/**
 * 
 * @ClassName: NettyBaseServiceImpl
 * @Description:
 * @see http://blog.csdn.net/u012486840/article/details/52937763
 * @author: zhaotf
 * @date: 2017年10月23日 下午8:50:43
 */
@Service("nettyBaseService")
public class NettyBaseServiceImpl implements NettyBaseService {
	private static Logger log = Logger.getLogger(NettyBaseServiceImpl.class);
	/**
	 * 服务端监听的端口地址
	 */
	private static final int portNumber = 8080;

	// 自动装备变量，spring会根据名字或者类型来装备这个变量，注解方式不需要set get方法了
	@Autowired
	private HelloServerInitializer helloServerInitializer;

	/**
	 * 
	 * @param uname
	 * @return Object
	 * @throws
	 */
	@Override
	public Object getMsg(String uname) {
		log.info("aa:" + uname);
		return "aaaccccccccccccsssssssssss";
	}

	// 程序初始方法入口注解，提示spring这个程序先执行这里
	@PostConstruct
	public void serverStart() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(helloServerInitializer);

			// 服务器绑定端口监听
			ChannelFuture f = b.bind(portNumber).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();

			log.info("###########################################");
			// 可以简写为
			/* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
