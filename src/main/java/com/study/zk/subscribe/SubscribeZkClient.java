package com.study.zk.subscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: SubscribeZkClient
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:09:40
 * @Version:1.0
 */
public class SubscribeZkClient {
	private static Logger logger = Logger.getLogger(SubscribeZkClient.class);

	// 需要多少个workserver
	private static final int CLIENT_QTY = 5;

	// private static final String ZOOKEEPER_SERVER =
	// "192.168.159.131:2181,192.168.159.131:2182,192.168.159.131:2183";// 公司
	private static final String ZOOKEEPER_SERVER = "192.168.0.126:2181,192.168.0.126:2182,192.168.0.126:2183";// V310
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

			ZkClient clientManage = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000,
					new BytesPushThroughSerializer());
			manageServer = new ManagerServer(SERVERS_PATH, COMMAND_PATH,
					CONFIG_PATH, clientManage, initConfig);
			manageServer.start();

			// 根据定义的work服务个数，创建服务器后注册，然后启动
			for (int i = 0; i < CLIENT_QTY; ++i) {
				initConfig = new ServerConfig();
				initConfig.setDbPwd("123456");
				initConfig.setDbUrl("jdbc:mysql://localhost:3306/mydb");
				initConfig.setDbUser("root");
				
				
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000,
						new BytesPushThroughSerializer());
				clients.add(client);
				ServerData serverData = new ServerData();
				serverData.setId(i);
				serverData.setName("WorkServer#" + i);
				serverData.setAddress("192.168.1." + i);

				WorkServer workServer = new WorkServer(CONFIG_PATH,
						SERVERS_PATH, serverData, client, initConfig);
				workServers.add(workServer);
				workServer.start();
			}

			logger.info("敲quit 回车键退出！\n");
			String line = null;
			while (!"quit".equals(line = new BufferedReader(new InputStreamReader(
					System.in)).readLine())) {
				logger.info("键盘输入aaaa:" + line);

			}

		} finally {
			// 将workserver和client给关闭

			logger.info("Shutting down...");

			for (WorkServer workServer : workServers) {
				try {
					logger.info("工作实例停止服务:" + Thread.currentThread().getId()
							+ "," + JSON.toJSONString(workServer));
					workServer.stop();
				} catch (Exception e) {
					logger.error("工作实例停止服务异常", e);
				}
			}
			for (ZkClient client : clients) {
				try {
					logger.info("客户端停止:" + Thread.currentThread().getId() + ","
							+ JSON.toJSONString(client));
					client.close();
				} catch (Exception e) {
					logger.error("客户端停止服务异常", e);
				}

			}
		}
	}
}
