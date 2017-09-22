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
	 * @param commandPath
	 *            Zookeeper中存放命令的节点路径
	 * @param configPath
	 * @param zkClient
	 * @param config
	 *            配置信息
	 */
	public ManagerServer(String serversPath, String commandPath, String configPath, ZkClient zkClient,
			ServerConfig config) {
		this.serversPath = serversPath;
		this.commandPath = commandPath;
		this.zkClient = zkClient;
		this.config = config;
		this.configPath = configPath;
		this.childListener = new IZkChildListener() {
			// 用于监听zookeeper中servers节点的子节点列表变化
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				// 更新服务器列表
				workServerList = currentChilds;

				logger.info("work server变更监听:" + Thread.currentThread().getId() + "," + parentPath + ","
						+ JSON.toJSONString(workServerList));
				execList();
			}

		};

		// 用于监听zookeeper中command节点的数据变化
		this.dataListener = new IZkDataListener() {

			public void handleDataDeleted(String dataPath) throws Exception {

			}

			public void handleDataChange(String dataPath, Object data) throws Exception {

				String cmd = new String((byte[]) data);
				logger.info("cmd:" + cmd);
				exeCmd(cmd);

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
			logger.error("命令错误:" + Thread.currentThread().getId() + "," + cmdType);
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
				zkClient.createPersistent(configPath, JSON.toJSONString(config).getBytes());
				logger.info(
						"创建节点:" + Thread.currentThread().getId() + "," + configPath + "," + JSON.toJSONString(config));
			} catch (ZkNodeExistsException e) {
				logger.error(e);
				// 节点已经存在异常，直接写入数据
				zkClient.writeData(configPath, JSON.toJSONString(config).getBytes());
				logger.info("写入节点信息:" + Thread.currentThread().getId() + "," + configPath + ","
						+ JSON.toJSONString(config));
			} catch (ZkNoNodeException e) {
				logger.error(e);
				// 表示其中的一个节点的父节点还没有被创建
				String parentDir = configPath.substring(0, configPath.lastIndexOf('/'));
				zkClient.createPersistent(parentDir, true);
				logger.info("创建节点:" + Thread.currentThread().getId() + "," + parentDir);
				execCreate();
			}
		}
	}

	/**
	 * 变更
	 * 
	 * void
	 */
	private void execModify() {
		config.setDbUser(config.getDbUser() + "_modify");
		try {
			// 回写到zookeeper中
			zkClient.writeData(configPath, JSON.toJSONString(config).getBytes());
			logger.info(
					"写入节点信息:" + Thread.currentThread().getId() + "," + configPath + "," + JSON.toJSONString(config));
		} catch (ZkNoNodeException e) {
			logger.error(e);
			execCreate();
		}
	}

}
