package com.hhcf.backend.service.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhcf.backend.service.impl.HelloServerHandler;

/**
 * 
 * @ClassName: HelloServerInitializer
 * @Description:
 * @see http://blog.csdn.net/u012486840/article/details/52937763
 * @author: zhaotf
 * @date: 2017年10月23日 下午8:52:14
 */
@Service("helloServerInitializer")
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
	@Autowired
	private HelloServerHandler helloServerHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 以("\n")为结尾分割的 解码器
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));

		// 字符串解码 和 编码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", helloServerHandler);
	}

}
