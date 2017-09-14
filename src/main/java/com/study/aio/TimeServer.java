package com.study.aio;

import java.io.IOException;

/**
 * 
 * @Title: TimeServer
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:10:37
 * @Version:1.0
 */
public class TimeServer {
	public static void main(String[] args) throws IOException {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {

			}
		}
		AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
		new Thread(timeServerHandler, "AIOServer").start();
	}
}
