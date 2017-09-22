package com.study.zk.conf.mg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @Title: AppClient
 * @Description:ZK 实现对APP集群节点监控管理：APP在线和离线能即时通知到监控程序
 * @see http://blog.csdn.net/lmx1989219/article/details/45768723
 * @Author: zhaotf
 * @Since:2017年9月22日 下午3:48:35
 * @Version:1.0
 */
public class AppClient {

	private String groupNode = "sgroup";
	private ZooKeeper zk;
	private Stat stat = new Stat();
	private volatile List<String> serverList;

	/**
	 * 连接zookeeper
	 */
	public void connectZookeeper() throws Exception {
		zk = new ZooKeeper("10.15.107.105:2181,10.15.107.42:2181,10.15.107.43:2181", 30 * 1000, new Watcher() {
			public void process(WatchedEvent event) {
				// 如果发生了"/sgroup"节点下的子节点变化事件, 更新server列表, 并重新注册监听
				if (event.getType() == EventType.NodeChildrenChanged && ("/" + groupNode).equals(event.getPath())) {
					try {
						updateServerList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateServerList();
	}

	/**
	 * 更新server列表
	 */
	private void updateServerList() throws Exception {
		List<String> newServerList = new ArrayList<String>();

		// 获取并监听groupNode的子节点变化
		// watch参数为true, 表示监听子节点变化事件.
		// 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
		List<String> subList = zk.getChildren("/" + groupNode, true);
		for (String subNode : subList) {
			// 获取每个子节点下关联的server地址
			byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);
			newServerList.add(new String(data, "utf-8"));
		}

		// 替换server列表
		serverList = newServerList;

		System.out.println("current active server address: " + serverList);
	}

	public static void main(String[] args) throws Exception {
		// 初始化log4j
		File f = new File("user.dir" + File.separator + "log");
		if (!f.exists()) {
			f.mkdir();
			new File("user.dir" + File.separator + "log" + File.separator + "land.log");
		}
		PropertyConfigurator.configureAndWatch(File.separator + System.getProperty("user.dir") + File.separator + "conf"
				+ File.separator + "log4j.properties");

		AppClient ac = new AppClient();
		ac.connectZookeeper();
		Thread.sleep(Long.MAX_VALUE);
	}
}
