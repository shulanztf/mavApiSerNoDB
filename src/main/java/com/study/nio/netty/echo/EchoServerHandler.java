package com.study.nio.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: EchoServerHandler
 * @Description:
 * @see http://www.jianshu.com/p/1123c9164e3e
 * @author: zhaotf
 * @date: 2017年10月21日 下午4:32:36
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = Logger
			.getLogger(EchoServerHandler.class.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.log(Level.WARN, "Unexpected exception from downstream.", cause);
		ctx.close();
	}
}
