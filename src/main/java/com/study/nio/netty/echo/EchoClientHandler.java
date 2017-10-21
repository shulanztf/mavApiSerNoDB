package com.study.nio.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: EchoClientHandler
 * @Description:
 * @see http://www.jianshu.com/p/1123c9164e3e
 * @author: zhaotf
 * @date: 2017年10月21日 下午7:04:33
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(EchoClientHandler.class.getName());

	private final ByteBuf firstMessage;

	/**
	 * Creates a client-side handler.
	 */
	public EchoClientHandler(int firstMessageSize) {
		if (firstMessageSize <= 0) {
			throw new IllegalArgumentException("firstMessageSize: "
					+ firstMessageSize);
		}
		firstMessage = Unpooled.buffer(firstMessageSize);
		for (int i = 0; i < firstMessage.capacity(); i++) {
			firstMessage.writeByte((byte) i);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(firstMessage);
	}

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
		// Close the connection when an exception is raised.
		logger.log(Level.WARN, "Unexpected exception from downstream.", cause);
		ctx.close();
	}
}
