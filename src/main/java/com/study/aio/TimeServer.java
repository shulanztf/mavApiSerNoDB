package com.study.aio;

import java.io.IOException;

/**
 * 
 * @Title: TimeServer
 * @Description:
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:10:37
 * @Version:1.0
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;
		AsyncTimeServerHandler serverHandler = new AsyncTimeServerHandler(port);
		new Thread(serverHandler, "AIOServer1").start();
		// new Thread(serverHandler, "AIOServer2").start();
	}

}
