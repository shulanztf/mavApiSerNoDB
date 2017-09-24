package com.study.zk.subscribe;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: WorkServer
 * @Description:
 * @see http://blog.csdn.net/zuoanyinxiang/article/details/50937892
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:18:37
 * @Version:1.0
 */
public class WorkServer {
	private static Logger logger = Logger.getLogger(WorkServer.class);
	private String serversPath;
	private String configPath;
	private ZkClient zkClient;
	private ServerConfig config;
	private ServerData serverData;

	private IZkDataListener dataListener;// 数据监听器

	/**
	 * 
	 * @param configPath
	 *            代表config节点的路径
	 * @param serversPath
	 *            代表servers节点的路径
	 * @param serverData
	 *            代表当前服务器的基本信息
	 * @param zkClient
	 *            底层与zookeeper集群通信的组件
	 * @param initconfig
	 *            当前服务器的初始配置
	 */
	public WorkServer(String configPath, String serversPath,
			ServerData serverData, ZkClient zkClient, ServerConfig initconfig) {
		logger.info("WS启动:" + Thread.currentThread().getId() + ",服务:"
				+ JSON.toJSONString(serverData) + ",配置:"
				+ JSON.toJSONString(initconfig));
		this.configPath = configPath;
		this.serversPath = serversPath;
		this.serverData = serverData;
		this.zkClient = zkClient;
		this.config = initconfig;

		// dataListener 用于监听config节点的数据改变
		this.dataListener = new IZkDataListener() {

			public void handleDataDeleted(String arg0) throws Exception {
				logger.info("配置删除监听获取信息:" + arg0);
			}

			/**
			 * 当数据的值改变时处理的 Object data,这个data是将ServerConfig对象转成json字符串存入
			 * 可以通过参数中的Object data 拿到当前数据节点最新的配置信息
			 * 拿到这个data信息后将它反序列化成ServerConfig对象，然后更新到自己的serverconfig属性中
			 */
			public void handleDataChange(String dataPath, Object data)
					throws Exception {
				logger.info("eeeeeeeee:"+Thread.currentThread().getId()+","+JSON.toJSONString(config));
				String retJson = new String((byte[]) data);
				logger.info("配置变更监听获取信息:" + Thread.currentThread().getId()
						+ "," + dataPath + "," + retJson);
				ServerConfig serverConfigLocal = (ServerConfig) JSON
						.parseObject(retJson, ServerConfig.class);
				// 更新配置
				updateConfig(serverConfigLocal);
			}
		};

		logger.info("aaaaaaaaaaa:" + Thread.currentThread().getId() + ","
				+ JSON.toJSONString(this.config));
	}

	/**
	 * 服务的启动
	 */
	public void start() {
		logger.info("ZK服务启动...:" + Thread.currentThread().getId()+","+JSON.toJSONString(config));
		initRunning();
	}

	/**
	 * 服务的停止
	 */
	public void stop() {
		logger.info("ZK服务停止，并取消监听...:" + Thread.currentThread().getId());
		// 取消监听
		zkClient.unsubscribeDataChanges(configPath, dataListener);
	}

	/**
	 * 服务器的初始化
	 */
	private void initRunning() {
		registMeToZookeeper();
		// 订阅config节点的改变
		logger.info("ZK服务设置监听...:" + Thread.currentThread().getId()+","+JSON.toJSONString(config));
		zkClient.subscribeDataChanges(configPath, dataListener);
	}

	/**
	 * 启动时向zookeeper注册自己
	 */
	private void registMeToZookeeper() {
		// 向zookeeper中注册自己的过程其实就是向servers节点下注册一个临时节点
		// 构造临时节点
		String mePath = serversPath.concat("/").concat(serverData.getAddress());
		try {
			if (!zkClient.exists(mePath)) {
				// 存入是将json序列化
				zkClient.createEphemeral(mePath, JSON.toJSONString(serverData)
						.getBytes());
				logger.info("创建节点，并配置信息:" + Thread.currentThread().getId()
						+ "," + mePath + "," + JSON.toJSONString(serverData));
			}
		} catch (ZkNoNodeException e) {
			// 父节点不存在
			zkClient.createPersistent(serversPath, true);
			registMeToZookeeper();
			logger.error("WS异常", e);
		}

	}

	/**
	 * 当监听到zookeeper中config节点的配置信息改变时，要读取配置信息来更新自己的配置信息
	 */
	private void updateConfig(ServerConfig serverConfig) {
		logger.info("更新前的配置信息aa:" + Thread.currentThread().getId() + ",前:"
				+ JSON.toJSONString(config) + ",后:"
				+ JSON.toJSONString(serverConfig));
//		this.config = serverConfig;
	}

}
