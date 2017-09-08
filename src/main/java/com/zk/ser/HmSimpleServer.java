package com.zk.ser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @Title: HmSimpleServer
 * @Description:zookeeper原理机制模拟，多服务端
 * @Author: zhaotf
 * @Since:2017年9月8日 上午8:13:47
 * @Version:1.0
 */
public class HmSimpleServer implements Runnable {
	private int port;

	public HmSimpleServer() {
	}

	public HmSimpleServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			System.out.println("zookeeper服务端启动:" + port);
			Socket socker = ss.accept();
			new Thread(new HmSimpleServerHandler(socker), "zk" + port).start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
