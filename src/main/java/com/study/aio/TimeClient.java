package com.study.aio;

import java.io.IOException;

/**
 * 
 * @Title: TimeClient
 * @Description:AIO 开发
 * @see http://www.cnblogs.com/hujiapeng/p/7233760.html
 * @Author: zhaotf
 * @Since:2017年9月14日 下午4:14:34
 * @Version:1.0
 */
public class TimeClient {
	public static void main(String[] args) {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);// 异常时，退出运行
			}
		}

		try {
			new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO Client1").start();
			new Thread(new AsyncHhcfClientHandler("127.0.0.1", port), "AIO-HH-Client2").start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 2; i < 20; i++) {
			try {
				new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO Client" + i).start();
				new Thread(new AsyncHhcfClientHandler("127.0.0.1", port), "AIO-HH-Client" + i).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
