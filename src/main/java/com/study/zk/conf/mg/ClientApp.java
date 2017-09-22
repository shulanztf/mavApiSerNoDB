package com.study.zk.conf.mg;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: ClientApp
 * @Description:ZooKeeper 笔记(3) 实战应用之【统一配置管理】
 * @see http://www.cnblogs.com/yjmyzz/p/4604947.html
 * @Author: zhaotf
 * @Since:2017年9月22日 下午3:59:53
 * @Version:1.0
 */
public class ClientApp {
	private static Logger logger = Logger.getLogger(ClientApp.class);
	private static String host = "192.168.159.131:2181,192.168.159.131:2182,192.168.159.131:2183";// 公司
	// String host =
	// "192.168.0.126:2181,192.168.0.126:2182,192.168.0.126:2183";//V310

	public static void main(String[] args) throws Exception {

		try {
			ConfigManager cfgManager = new ConfigManager();
			ClientApp clientApp = new ClientApp();

			// 模拟【配置管理中心】初始化时，从db加载配置初始参数
			cfgManager.loadConfigFromDB();
			// 然后将配置同步到ZK
			cfgManager.syncFtpConfigToZk();

			// 模拟客户端程序运行
			clientApp.run();

			// 模拟配置修改
			cfgManager.updateFtpConfigToDB(23, "10.6.12.34", "newUser", "newPwd");
			cfgManager.syncFtpConfigToZk();

			// 模拟客户端自动感知配置变化
			clientApp.run();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

	}

	private FtpConfig ftpConfig;
	public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";

	public static ZkClient getZkClient() {
		return new ZkClient(host);
	}

	/**
	 * ZK配置变更监听获取
	 * 
	 * @return FtpConfig
	 */
	private FtpConfig getFtpConfig() {
		if (ftpConfig == null) {
			// 首次获取时，连接zk取得配置，并监听配置变化
			ZkClient zk = getZkClient();
			ftpConfig = (FtpConfig) zk.readData(FTP_CONFIG_NODE_NAME);
			logger.info("监听ZK配置，获取ZK服务端配置:" + JSON.toJSONString(ftpConfig));

			zk.subscribeDataChanges(FTP_CONFIG_NODE_NAME, new IZkDataListener() {

				@Override
				public void handleDataChange(String dataPath, Object data) throws Exception {
					logger.info("配置修改，节点:" + dataPath + ",数据:" + data.toString());
					ftpConfig = (FtpConfig) data;// 重新加载FtpConfig
				}

				@Override
				public void handleDataDeleted(String dataPath) throws Exception {
					logger.info("配置删除，节点:" + dataPath);
					ftpConfig = null;
				}
			});
		} else {
			logger.info("监听ZK配置，配置不为空:" + JSON.toJSONString(ftpConfig));
		}
		return ftpConfig;

	}

	/**
	 * 模拟程序运行
	 *
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {

		getFtpConfig();

		upload();

		download();
	}

	public void upload() throws InterruptedException {
		logger.info("正在上传文件..." + ftpConfig);
		TimeUnit.SECONDS.sleep(2);
		logger.info("文件上传完成..." + ftpConfig);
	}

	public void download() throws InterruptedException {
		logger.info("正在下载文件..." + ftpConfig);
		TimeUnit.SECONDS.sleep(2);
		logger.info("文件下载完成..." + ftpConfig);
	}

}
