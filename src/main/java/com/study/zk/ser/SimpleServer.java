package com.study.zk.ser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @Title: SimpleServer
 * @Description:zookeeper学习
 * @Author: zhaotf
 * @Since:2017年9月6日 下午5:36:45
 * @Version:1.0
 */
public class SimpleServer implements Runnable {

	public static void main(String[] args) throws IOException {
		int port = 18080;
		SimpleServer server = new SimpleServer(port);
		Thread thread = new Thread(server);
		thread.start();
	}

	private int port;

	public SimpleServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			Socket socket = null;
			while (true) {
				socket = server.accept();
				new Thread(new SimpleServerHandler(socket)).start();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
