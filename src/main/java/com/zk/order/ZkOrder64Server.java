package com.zk.order;

import java.util.Arrays;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @Title: ZkOrder64Server
 * @Description:
 * @see http://aiilive.blog.51cto.com/1925756/1685614
 * @Author: zhaotf
 * @Since:2017年9月21日 上午9:52:43
 * @Version:1.0
 */
public class ZkOrder64Server implements Runnable, IZkChildListener {
	Logger logger = Logger.getLogger(ZkOrder64Server.class);
	// 提前创建好锁对象的结点"/lock" CreateMode.PERSISTENT
	private static final String LOCK_ZNODE = "/lock";
	/** 线程名称 */
	private final String taskName;
	private final ZkClient zkClient;

	private final String lockPrefix = "/loc";
	private final String selfZnode;

	public ZkOrder64Server(String taskName) {
		this.taskName = taskName;
		zkClient = new ZkClient("192.168.88.153:2181", 30000, 10000);
		selfZnode = zkClient.createEphemeralSequential(LOCK_ZNODE + lockPrefix, new byte[0]);
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
		String[] childrensZnode = currentChilds.toArray(new String[currentChilds.size()]);
		Arrays.sort(childrensZnode);
		String minZnode = LOCK_ZNODE + "/" + childrensZnode[0];
		if (selfZnode.equals(minZnode)) {
			createSeq();
			zkClient.unsubscribeChildChanges(LOCK_ZNODE, this);
			zkClient.delete(selfZnode);
			zkClient.close();
		}
	}

	@Override
	public void run() {
		// 执行订阅command节点数据变化和servers节点的列表变化
		zkClient.subscribeChildChanges(LOCK_ZNODE, this);
		// do {
		// } while (zkClient.isConnected());
	}

	private void createSeq() {
		Stat stat = new Stat();
		byte[] oldData = zkClient.readData(LOCK_ZNODE, stat);
		byte[] newData = update(oldData);
		zkClient.writeData(LOCK_ZNODE, newData);
		System.out.println(taskName + selfZnode + " obtain seq=" + new String(newData));
	}

	private byte[] update(byte[] currentData) {
		String s = new String(currentData);
		int d = Integer.parseInt(s);
		d = d + 1;
		s = String.valueOf(d);
		return s.getBytes();
	}
}
