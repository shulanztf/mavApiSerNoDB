package com.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * RPC远程服务调用 thrift 线程池
 * 
 * @author zhaotf 2017年7月2日 下午4:00:35
 *
 */
public class HelloServerDemo1 {
	public static final int SERVER_PORT = 8090;

	public void startServer() {
		try {
			System.out.println("HelloWorld TThreadPoolServer start ....");

			TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
					new HelloWorldImpl());

			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
					serverTransport);
			ttpsArgs.processor(tprocessor);
			ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());

			// 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			TServer server = new TThreadPoolServer(ttpsArgs);
			server.serve();

		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HelloServerDemo server = new HelloServerDemo();
		server.startServer();
	}
}
