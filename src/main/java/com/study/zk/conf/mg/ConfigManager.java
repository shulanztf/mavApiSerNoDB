package com.study.zk.conf.mg;

import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: ConfigManager
 * @Description:ZooKeeper 笔记(3) 实战应用之【统一配置管理】
 * @see http://www.cnblogs.com/yjmyzz/p/4604947.html
 * @Author: zhaotf
 * @Since:2017年9月22日 下午3:59:20
 * @Version:1.0
 */
public class ConfigManager {
	private static Logger logger = Logger.getLogger(ConfigManager.class);
	private FtpConfig ftpConfig;

	/**
	 * 模拟从db加载初始配置
	 */
	public void loadConfigFromDB() {
		ftpConfig = new FtpConfig(21, "192.168.1.1", "test", "123456");
		logger.info("加载数据库内 配置信息:" + JSON.toJSONString(ftpConfig));
	}

	/**
	 * 模拟更新DB中的配置
	 *
	 * @param port
	 * @param host
	 * @param user
	 * @param password
	 */
	public void updateFtpConfigToDB(int port, String host, String user, String password) {
		if (ftpConfig == null) {
			ftpConfig = new FtpConfig();
		}
		logger.info("更新数据库配置前:" + JSON.toJSON(ftpConfig));
		ftpConfig.setPort(port);
		ftpConfig.setHost(host);
		ftpConfig.setUser(user);
		ftpConfig.setPassword(password);
		logger.info("更新数据库配置后:" + JSON.toJSON(ftpConfig));
	}

	/**
	 * 将配置同步到ZK
	 * 
	 * @throws Exception
	 */
	public void syncFtpConfigToZk() throws Exception {
		ZkClient zk = ClientApp.getZkClient();
		try {
			if (!zk.exists(ClientApp.FTP_CONFIG_NODE_NAME)) {
				zk.createPersistent(ClientApp.FTP_CONFIG_NODE_NAME, true);
			}
			zk.writeData(ClientApp.FTP_CONFIG_NODE_NAME, ftpConfig);
			logger.info("配置同步至ZK:" + JSON.toJSONString(ftpConfig));
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			if (zk != null) {
				zk.close();
			}
		}
	}

}
