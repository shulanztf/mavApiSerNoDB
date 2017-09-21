package com.zk.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @Title: ZkOrder32Server
 * @Description:利用Zookeeper的znode数据版本生成序列号,32位序列号
 * @see http://aiilive.blog.51cto.com/1925756/1685614
 * @Author: zhaotf
 * @Since:2017年9月21日 上午8:02:08
 * @Version:1.0
 */
public class ZkOrder32Server implements Runnable {
	Logger logger = Logger.getLogger(ZkOrder32Server.class);
	// 提前创建好存储Seq的"/createSeq"结点 CreateMode.PERSISTENT
	public static final String SEQ_ZNODE = "/seqorderno";
	private final String threadName;

	public ZkOrder32Server(String taskName) {
		this.threadName = taskName;
	}

	@Override
	public void run() {
		ZkClient zkClient = new ZkClient("192.168.159.131:2181", 3000, 1000);
		// zkClient.createPersistentSequential(SEQ_ZNODE + "/HHCF", threadName +
		// System.currentTimeMillis());// 创建订单节点，持久、有序
		Object data = zkClient.getChildren("/");
		logger.info("zk操作:" + threadName + "," + data.toString());

		zkClient.close();
	}

	/**
	 * 删除节点
	 * 
	 * @param nodeName
	 *            节点名称前缀
	 */
	public void delNodes(ZkClient zkClient, Object data, String nodeName) {
		if (!(data instanceof ArrayList)) {
			return;
		}
		List<?> list = (List<?>) data;
		for (Object obj : list) {
			if (StringUtils.startsWith(obj.toString(), nodeName)) {
				logger.info("节点删除操作:" + "/" + obj + ",删除结果:" + zkClient.delete("/" + obj));
			}
		}
	}

	public static void main(String[] args) {
		final ExecutorService es = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 1; i++) {
			es.execute(new ZkOrder32Server("thread-name" + i));
		}
		es.shutdown();// 关闭资源
	}

}
