package com.study.zk.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @ClassName: DistributedSystemServer
 * @Description:基于Zookeeper服务注册和发现
 * @see http://blog.csdn.net/zbw18297786698/article/details/54426970
 * @author: zhaotf
 * @date: 2017年9月23日 下午8:30:24
 */
public class DistributedSystemServer {
	// zk服务器列表
	public static final String ZK_HOSTS = "192.168.0.126:2181,192.168.0.126:2182,192.168.0.126:2183";// V310
	// 连接的超时时间
	public static final int SESSION_TIMEOUT = 2000;
	// 服务在zk下的路径
	public static final String PARENT_ZNODE_PATH = "/servers";
	private ZooKeeper zk = null;

	private void getZkClient() throws Exception {

		// 服务器在需求中并不需要做任何监听
		zk = new ZooKeeper(ZK_HOSTS, SESSION_TIMEOUT, null);

	}

	/**
	 * 向zookeeper中的/servers下创建子节点
	 * 
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	private void connectZK(String serverName, String port) throws Exception {

		// 先创建出父节点
		if (zk.exists(PARENT_ZNODE_PATH, false) == null) {
			zk.create(PARENT_ZNODE_PATH, null, Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}

		// 连接zk创建znode
		zk.create(PARENT_ZNODE_PATH + "/", (serverName + ":" + port).getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("server " + serverName + " is online ......");

	}

	// 服务器的具体业务处理功能
	private void handle(String serverName) throws Exception {
		System.out.println("server " + serverName
				+ " is waiting for task process......");
		Thread.sleep(Long.MAX_VALUE);

	}

	public static void main(String[] args) throws Exception {

		DistributedSystemServer server = new DistributedSystemServer();

		// 获取与zookeeper通信的客户端连接
		server.getZkClient();

		// 一启动就去zookeeper上注册服务器信息，参数1： 服务器的主机名 参数2：服务器的监听端口
		server.connectZK("127.0.0.1", "8080");

		// 进入业务逻辑处理流程
		server.handle("127.0.0.1");

	}

}
