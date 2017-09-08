package com.zk.ser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Title: HmSimpleServerHandler
 * @Description:zookeeper原理机制模拟，多服务端
 * @Author: zhaotf
 * @Since:2017年9月8日 上午8:21:05
 * @Version:1.0
 */
public class HmSimpleServerHandler implements Runnable {
	private Socket socket;

	public HmSimpleServerHandler() {
	}

	public HmSimpleServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(this.socket.getOutputStream(), true);
			String line = null;
			while (true) {
				line = br.readLine();// 读入数据
				if (StringUtils.isBlank(line)) {
					break;
				}
				System.out.println(socket.getPort() + "接收到的数据行:" + line);
				pw.println(line);// 输出数据
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
			if (this.socket != null) {
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.socket = null;
			}
		}

	}

}
