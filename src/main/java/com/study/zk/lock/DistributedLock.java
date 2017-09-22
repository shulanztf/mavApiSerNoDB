package com.study.zk.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName: DistributedLock
 * @Description:基于zookeeper简单实现分布式锁
 * @see http://blog.csdn.net/peace1213/article/details/52571445
 * @see https://www.2cto.com/kf/201603/493045.html
 * @author: zhaotf
 * @param <privte>
 * @date: 2017年9月17日 下午4:20:02
 */
public class DistributedLock<privte> implements Lock, Watcher {
	private static Logger logger = Logger.getLogger(DistributedLock.class);

	public static void main(String[] args) throws Exception {
		String host = "192.168.159.131:2181,192.168.159.131:2182,192.168.159.131:2183";// 公司
		// String host =
		// "192.168.0.126:2181,192.168.0.126:2182,192.168.0.126:2183";//V310
		final DistributedLock lock = new DistributedLock(host, "lock");
		ExecutorService es = Executors.newFixedThreadPool(3);// 线程池

		for (int i = 0; i < 3; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					try {
						lock.lock();// 获取锁
					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						// 共享资源
						if (lock != null) {
							lock.unlock();// 释放锁
							lock.latch.countDown();
							logger.info("线程关闭了ZK服务:" + Thread.currentThread().getId());

						}
					}
				}
			});
		}
		// try {
		// lock.zk.close(); // 关闭ZK服务，服务暂时不关闭
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		es.shutdown();
		logger.info("线程池关闭:" + Thread.currentThread().getId());
	}

	private ZooKeeper zk;
	private String root = "/order";// 根
	private String lockName;// 竞争资源的标志
	// private volatile String waitNode;// 等待前一个锁
	private ThreadLocal<String> myZnode = new ThreadLocal<String>();// 当前锁
	private CountDownLatch latch = new CountDownLatch(1);;// 计数器
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	private int sessionTimeout = 30000;
	private ThreadLocal<List<String>> lockObjNodes = new ThreadLocal<List<String>>();
	private ThreadLocal<List<String>> subNodes = new ThreadLocal<List<String>>();

	/**
	 * 创建分布式锁,使用前请确认config配置的zookeeper服务可用
	 * 
	 * @param config
	 *            192.168.1.127:2181 连接到ZK服务，多个可以用逗号分割写
	 * @param lockName
	 *            竞争资源标志,lockName中不能包含单词_lock_
	 */
	public DistributedLock(String config, String lockName) {
		this.lockName = lockName;
		try {
			// 连接到ZK服务，多个可以用逗号分割写
			zk = new ZooKeeper(config, sessionTimeout, this);
			// connectedSignal.await();
			Stat stat = zk.exists(root, false);// 此去不执行 Watcher
			if (stat == null) {
				// 创建根节点
				zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * zookeeper节点的监视器
	 */
	@Override
	public void process(WatchedEvent event) {
		logger.info("ZK监听事件:" + JSON.toJSONString(event));

		if (KeeperState.SyncConnected == event.getState()) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				// 连接时的监听事件
				connectedSignal.countDown();
				logger.info("ZK服务可用:" + this.myZnode.get() + "," + Thread.currentThread().getId());
			} else if (EventType.NodeCreated == event.getType()) {
				// 创建节点事件
				logger.info("success create znode");
				logger.info("创建节点成功:" + this.myZnode.get() + "," + Thread.currentThread().getId());
			} else if (EventType.NodeDataChanged == event.getType()) {
				// 更新节点事件
				logger.info("success change znode: " + event.getPath());
				logger.info("更新节点成功:" + this.myZnode.get() + "," + Thread.currentThread().getId());
			} else if (EventType.NodeDeleted == event.getType()) {
				// 删除节点事件
				// 前一节点删除时触发监听，获取锁
				logger.info("删除节点成功:" + this.myZnode.get() + "," + Thread.currentThread().getId() + ","
						+ JSON.toJSONString(event));
				this.latch.countDown();
			} else if (EventType.NodeChildrenChanged == event.getType()) {
				// 子节点更新事件
				logger.info("NodeChildrenChanged");
				logger.info("更新子节点成功:" + this.myZnode.get() + "," + Thread.currentThread().getId());
			}
		}
	}

	@Override
	public void lock() {
		try {
			if (this.tryLock()) {
				logger.info("获取锁，线程:" + Thread.currentThread().getId() + "," + myZnode.get() + " 获取锁 true");
				return;
			} else {
				// TODO 阻塞问题点

				// 未获取锁时，找到前一节点，并对其注册监听
				logger.info("线程未获取锁:" + Thread.currentThread().getId() + "," + myZnode.get());
				String subMyZnode = myZnode.get().substring(myZnode.get().lastIndexOf("/") + 1); // 如果不是最小的节点，找到比自己小1的节点
				String localWaitNode = lockObjNodes.get()
						.get(Collections.binarySearch(lockObjNodes.get(), subMyZnode) - 1);// 找到前一个子节点
				// 对前一节点注册监听
				if (zk.exists(root + "/" + localWaitNode, true) != null) {
					logger.info(
							"开始等待，并注册监听:" + Thread.currentThread().getId() + "," + myZnode.get() + "," + localWaitNode);
					waitForLock(localWaitNode, sessionTimeout);// 等待锁，前一节点删除时，会通知自己去获取锁
				}
				// 前一节点不存在时，或前一节点删除时，重新获取锁
				logger.info("再次获取锁:" + myZnode.get() + "," + Thread.currentThread().getId() + ",前一节点:" + localWaitNode);
				// this.lock();
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加锁
	 */
	@Override
	public boolean tryLock() {
		try {
			String splitStr = "_lock_";
			if (lockName.contains(splitStr)) {
				throw new RuntimeException("lockName 不兼容 \\u000B");
			}
			// 判断是否已有锁节点(临时子节点)
			if (StringUtils.isBlank(myZnode.get())) {
				// 创建临时子节点
				myZnode.set(zk.create(root + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.EPHEMERAL_SEQUENTIAL));
				logger.info(myZnode.get() + " 节点创建完, " + Thread.currentThread().getId());
				// 取出所有子节点
				// List<String> subNodes = zk.getChildren(root, false);
				subNodes.set(zk.getChildren(root, false));
				Collections.sort(subNodes.get());
				// 取出所有lockName的锁
				lockObjNodes.set(new ArrayList<String>());
				for (String node : subNodes.get()) {
					String _node = node.split(splitStr)[0];
					if (!_node.equals(lockName)) {
						continue;
					}
					lockObjNodes.get().add(node);
				}
				logger.info("等待节点列表加载AAA:" + myZnode.get() + "," + lockObjNodes.get() + ","
						+ Thread.currentThread().getId());
				// lockObjNodes.set(nodes);
			}

			logger.info("比较:" + myZnode.get() + "," + lockObjNodes.get().get(0) + "," + Thread.currentThread().getId()
					+ "," + JSON.toJSONString(lockObjNodes.get()));
			if (myZnode.get().equals(root + "/" + lockObjNodes.get().get(0))) {
				// 如果是最小的节点,则表示取得锁
				logger.info("线程获取了锁:" + myZnode.get() + "==" + lockObjNodes.get().get(0) + ","
						+ Thread.currentThread().getId());
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	/**
	 * 等待锁
	 * 
	 * @param lower
	 *            监听目标节点
	 * @param waitTime
	 * @return boolean
	 */
	private boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
		logger.info("线程:" + Thread.currentThread().getId() + " 开始等待锁 " + root + "/" + lower);
		this.latch.await(waitTime, TimeUnit.MILLISECONDS);// 等待，这里应该一直等待其他线程释放锁

		// // TODO 检查自己前面是否还有节点未存在，有，就再次等待
		// for (String node : lockObjNodes.get()) {
		// if (StringUtils.endsWith(myZnode.get(), node)) {
		// // 到了自己，就跳出
		// continue;
		// }
		// Stat sat = zk.exists(root + "/" + node, false);//
		// if (sat == null) {
		// lockObjNodes.get().remove(node);
		// logger.info("删除不存在的等待节点:" + Thread.currentThread().getId() + "," +
		// myZnode.get() + "," + node + ","
		// + JSON.toJSONString(lockObjNodes.get()));
		// }
		// }

		return true;
	}

	/**
	 * 释放锁
	 */
	@Override
	public void unlock() {
		try {
			logger.info("释放锁，线程:" + Thread.currentThread().getId() + ",本节点删除:" + myZnode.get());
			zk.delete(myZnode.get(), -1);// 删除自身节点，并触发后一节点的监听
			// myZnode.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) {
		try {
			if (this.tryLock()) {
				return true;
			}
			// 如果不是最小的节点，找到比自己小1的节点
			String tmpNode = myZnode.get();
			String subMyZnode = tmpNode.substring(tmpNode.lastIndexOf("/") + 1);
			String localWaitNode = lockObjNodes.get().get(Collections.binarySearch(lockObjNodes.get(), subMyZnode) - 1);// 找到前一个子节点
			logger.info("开始等待:" + localWaitNode + "," + Thread.currentThread().getId());
			return waitForLock(localWaitNode, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		this.lock();
	}

	@Override
	public Condition newCondition() {
		return null;
	}

}
