package com.study.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.study.nio.util.CalculatorUtils;

/**
 * 
 * @Title: BioServerHandler
 * @Description:BIO 客户端线程 用于处理一个客户端的Socket链路
 * @see http://blog.csdn.net/anxpp/article/details/51512200
 * @Author: zhaotf
 * @Since:2017年9月13日 上午8:09:28
 * @Version:1.0
 */
public class BioServerHandler implements Runnable {
	private Socket socket;

	public BioServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String expression;
			String result;
			while (true) {
				// 通过BufferedReader读取一行
				// 如果已经读到输入流尾部，返回null,退出循环
				// 如果得到非空值，就尝试计算结果并返回
				if ((expression = in.readLine()) == null)
					break;
				System.out.println("服务器收到消息:" + expression);
				try {
					result = CalculatorUtils.cal(expression).toString();
				} catch (Exception e) {
					result = "计算错误:" + e.getMessage();
				}
				out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 一些必要的清理工作
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket = null;
			}
		}
	}

}
