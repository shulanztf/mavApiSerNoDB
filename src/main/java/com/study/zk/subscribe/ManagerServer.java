package com.study.zk.subscribe;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: ManagerServer
 * @Description:Zookeeper学习(八)：Zookeeper的数据发布与订阅模式
 * @see http://blog.csdn.net/zuoanyinxiang/article/details/50937892
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:05:57
 * @Version:1.0
 */
public class ManagerServer {
	private static Logger logger = Logger.getLogger(ManagerServer.class);
	private String serversPath;// 服务器列表节点
	private String commandPath;// 命令节点
	private String configPath;// 配置节点
	private ZkClient zkClient;
	private ServerConfig config;
	// 用于监听zookeeper中servers节点的子节点列表变化
	private IZkChildListener childListener;
	// 用于监听zookeeper中command节点的数据变化
	private IZkDataListener dataListener;
	// 工作服务器的列表
	private List<String> workServerList;

	/**
	 * 
	 * @param serversPath
	 *            服务列表节点
	 * @param commandPath
	 *            Zookeeper中存放命令的节点路径
	 * @param configPath
	 *            配置节点
	 * @param zkClient
	 *            ZK组件
	 * @param config
	 *            配置信息
	 */
	public ManagerServer(String serversPath, String commandPath,
			String configPath, ZkClient zkClient, ServerConfig config) {
		this.serversPath = serversPath;
		this.commandPath = commandPath;
		this.zkClient = zkClient;
		this.config = config;
		this.configPath = configPath;
		this.childListener = new IZkChildListener() {
			// 用于监听zookeeper中servers节点的子节点列表变化
			@Override
			public void handleChildChange(String parentPath,
					List<String> currentChilds) throws Exception {

				logger.info("work server子节点变更监听:"
						+ Thread.currentThread().getId() + "," + parentPath
						+ ",原有:" + JSON.toJSONString(workServerList) + ",最新:"
						+ JSON.toJSONString(currentChilds));
				workServerList = currentChilds; // 更新服务器列表
				execList();
			}

		};

		// 用于监听zookeeper中command节点的数据变化
		this.dataListener = new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				logger.info("work server节点/数据删除监听:"
						+ Thread.currentThread().getId() + "," + dataPath);
			}

			@Override
			public void handleDataChange(String dataPath, Object data)
					throws Exception {
				String cmd = new String((byte[]) data);
				logger.info("work server节点/数据变更监听:"
						+ Thread.currentThread().getId() + "," + dataPath + ","
						+ cmd);
				exeCmd(cmd);// 执行控制命令的函数
			}

		};

	}

	public void start() {
		initRunning();
	}

	public void stop() {
		// 取消订阅command节点数据变化和servers节点的列表变化
		zkClient.unsubscribeChildChanges(serversPath, childListener);
		zkClient.unsubscribeDataChanges(commandPath, dataListener);
		logger.info("撤销监听" + Thread.currentThread().getId() + ",服务节点:"
				+ serversPath + ",命令节点:" + commandPath);
	}

	/**
	 * 初始化
	 */
	private void initRunning() {
		// 执行订阅command节点数据变化和servers节点的列表变化
		zkClient.subscribeDataChanges(commandPath, dataListener);
		zkClient.subscribeChildChanges(serversPath, childListener);
	}

	/**
	 * 执行控制命令的函数 1: list 2: create 3: modify
	 */
	private void exeCmd(String cmdType) {
		if ("list".equals(cmdType)) {
			execList();
		} else if ("create".equals(cmdType)) {
			execCreate();
		} else if ("modify".equals(cmdType)) {
			execModify();
		} else {
			logger.error("命令错误:" + Thread.currentThread().getId() + ","
					+ cmdType);
		}
	}

	private void execList() {
		logger.info("执行命令列表服务:" + workServerList.toString());
	}

	/**
	 * 创建
	 * 
	 * void
	 */
	private void execCreate() {
		if (!zkClient.exists(configPath)) {
			try {
				zkClient.createPersistent(configPath, JSON.toJSONString(config)
						.getBytes());
				logger.info("创建节点:" + Thread.currentThread().getId() + ","
						+ configPath + "," + JSON.toJSONString(config));
			} catch (ZkNodeExistsException e) {
				// 节点已经存在异常，直接写入数据
				zkClient.writeData(configPath, JSON.toJSONString(config)
						.getBytes());
				logger.error("创建节点异常,重试写入数据:" + Thread.currentThread().getId()
						+ "," + configPath + "," + JSON.toJSONString(config), e);
			} catch (ZkNoNodeException e) {
				// 表示其中的一个节点的父节点还没有被创建
				String parentDir = configPath.substring(0,
						configPath.lastIndexOf('/'));
				zkClient.createPersistent(parentDir, true);
				execCreate();
				logger.error("创建节点异常，重试创建父节点:" + Thread.currentThread().getId()
						+ "," + configPath + "," + parentDir, e);
			}
		}
	}

	/**
	 * 变更
	 * 
	 * void
	 */
	private void execModify() {
		config.setDbUser(config.getDbUser() + "_modify" + ","
				+ Thread.currentThread().getId() + ":"
				+ System.currentTimeMillis());
		config.setDbPwd(config.getDbPwd() + ","
				+ Thread.currentThread().getId() + ":"
				+ System.currentTimeMillis());
		try {
			// 回写到zookeeper中
			logger.info("写入节点信息:" + Thread.currentThread().getId() + ","
					+ configPath + "," + JSON.toJSONString(config));
			zkClient.writeData(configPath, JSON.toJSONString(config).getBytes());
		} catch (ZkNoNodeException e) {
			logger.error("节点数据变更异常:" + Thread.currentThread().getId(), e);
			execCreate();
		}
	}

}
