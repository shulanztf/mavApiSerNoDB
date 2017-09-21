package com.zk.subscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 
 * @Title: SubscribeZkClient
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:09:40
 * @Version:1.0
 */
public class SubscribeZkClient {

	// 需要多少个workserver
	private static final int CLIENT_QTY = 5;

	private static final String ZOOKEEPER_SERVER = "192.168.30.164:2181,192.168.30.165:2181,192.168.30.166:2181";
	// 节点的路径
	private static final String CONFIG_PATH = "/config";// 配置节点
	private static final String COMMAND_PATH = "/command";// 命令节点
	private static final String SERVERS_PATH = "/servers";// 服务器列表节点

	public static void main(String[] args) throws Exception {
		// 用来存储所有的clients
		List<ZkClient> clients = new ArrayList<ZkClient>();
		// 用来存储所有的workservers
		List<WorkServer> workServers = new ArrayList<WorkServer>();
		ManagerServer manageServer = null;

		try {
			ServerConfig initConfig = new ServerConfig();
			initConfig.setDbPwd("123456");
			initConfig.setDbUrl("jdbc:mysql://localhost:3306/mydb");
			initConfig.setDbUser("root");

			ZkClient clientManage = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new BytesPushThroughSerializer());
			manageServer = new ManagerServer(SERVERS_PATH, COMMAND_PATH, CONFIG_PATH, clientManage, initConfig);
			manageServer.start();

			// 根据定义的work服务个数，创建服务器后注册，然后启动
			for (int i = 0; i < CLIENT_QTY; ++i) {
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new BytesPushThroughSerializer());
				clients.add(client);
				ServerData serverData = new ServerData();
				serverData.setId(i);
				serverData.setName("WorkServer#" + i);
				serverData.setAddress("192.168.1." + i);

				WorkServer workServer = new WorkServer(CONFIG_PATH, SERVERS_PATH, serverData, client, initConfig);
				workServers.add(workServer);
				workServer.start();

			}
			System.out.println("敲回车键退出！\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();

		} finally {
			// 将workserver和client给关闭

			System.out.println("Shutting down...");

			for (WorkServer workServer : workServers) {
				try {
					workServer.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (ZkClient client : clients) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}
