package com.study.zk.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName: DistributedZCLock
 * @Description: zookeeper简单实现分布式锁,已调通,可单例/多线程 运行，第三方组件ZC
 * @author: zhaotf
 * @date: 2017年9月23日 上午8:50:50
 */
public class DistributedZCLock {
	private static Logger logger = Logger.getLogger(DistributedZCLock.class);

	public static void main(String[] args) {
		ExecutorService ec = Executors.newFixedThreadPool(50);
		try {
			final DistributedZCLock zcLock = new DistributedZCLock();
			// for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 5; i++) {
				ec.execute(new Runnable() {
					@Override
					public void run() {
						try {
							if (zcLock.getLock()) {
								zcLock.unlock();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (KeeperException e) {
							e.printStackTrace();
						}
					}
				});
			}
			TimeUnit.SECONDS.sleep(2);
			// }

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常A:" + e);
			logger.error("异常关闭:" + JSON.toJSONString(ec.shutdownNow()));

		} finally {
			ec.shutdown();
			logger.info("最终处理:" + Thread.currentThread().getId());
		}

	}

	// String host =
	// "192.168.159.131:2181,192.168.159.131:2182,192.168.159.131:2183";//
	// 公司
	private static String host = "192.168.0.126:2181,192.168.0.126:2182,192.168.0.126:2183";// V310
	private String root = "/order";// 根
	private String lockName = "lock_";// 竞争资源的标志
	// private ZooKeeper zk;// ZK自带组件，负责修改ZK服务器状态、数据
	private ZkClient zc;// ZK第三方组件，负责监听，接收信息，并修改自身
	private ThreadLocal<String> waitNode = new ThreadLocal<String>();// 等待前一个锁
	private ThreadLocal<Integer> waitNodeIndex = new ThreadLocal<Integer>();// 等待前一个锁
	private ThreadLocal<String> myZnode = new ThreadLocal<String>();// 当前锁
	private CountDownLatch latch = new CountDownLatch(1);// 计数器
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	// private int sessionTimeout = 30000;
	private ThreadLocal<List<String>> lockNodes = new ThreadLocal<List<String>>();// 所有锁节点
	private ThreadLocal<List<String>> subNodes = new ThreadLocal<List<String>>();// 辅助容器
	// 用于监听zookeeper中servers节点的子节点列表变化
	private IZkChildListener childListener;
	// 用于监听zookeeper中command节点的数据变化
	private IZkDataListener dataListener;
	//
	private IZkStateListener stateListener;

	public DistributedZCLock() throws InterruptedException, KeeperException {
		// zc = new ZkClient(host, 5000, 5000, new
		// BytesPushThroughSerializer());
		zc = new ZkClient(host, 5000, 5000, new ZkSerializer() {

			@Override
			public byte[] serialize(Object data) throws ZkMarshallingError {
				return (byte[]) data;
			}

			@Override
			public Object deserialize(byte[] bytes) throws ZkMarshallingError {
				return new String(bytes);
			}
		});
		this.subscribe(DistributedZCLock.this);// 注册监听
		// try {
		// connectedSignal.await();
		// } catch (InterruptedException e) {
		// logger.error(e);
		// }

	}

	/**
	 * 创建节点，并获取锁
	 * 
	 * @return boolean
	 * @throws
	 */
	public boolean lock() {
		myZnode.set(zc.createEphemeralSequential(root + "/" + lockName,
				("节点数据,threadId:" + Thread.currentThread().getId()).getBytes()));// 创建锁节点,并保存到线程缓存中
		subNodes.set(zc.getChildren(root));// 获取所有子节点
		Collections.sort(subNodes.get());
		lockNodes.set(new ArrayList<String>());
		logger.info("创建节点处理:" + Thread.currentThread().getId() + ","
				+ myZnode.get() + "," + subNodes.get());
		for (String node : subNodes.get()) {
			if (!StringUtils.startsWith(node, lockName)) {
				continue;
			}
			lockNodes.get().add(root + "/" + node);
		}
		logger.info("设置所有节点集合:" + Thread.currentThread().getId() + ","
				+ myZnode.get() + "," + lockNodes.get());
		if (StringUtils.equals(myZnode.get(), lockNodes.get().get(0))) {
			// 首节点获取锁
			logger.info("线程获取了锁:" + Thread.currentThread().getId() + ""
					+ this.myZnode.get());
			return true;
		}

		waitNodeIndex.set(new Integer(
				lockNodes.get().indexOf(myZnode.get()) - 1));
		waitNode.set(lockNodes.get().get(waitNodeIndex.get()));// 设置等待节点信息
		this.subscribe(this, lockNodes.get().get(waitNodeIndex.get()));// 设置监听
		logger.info("线程未获取锁:" + Thread.currentThread().getId() + ","
				+ myZnode.get() + "," + waitNodeIndex.get() + ","
				+ waitNode.get() + "," + lockNodes.get());
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
	private boolean waitForLock(String lower, long waitTime)
			throws InterruptedException, KeeperException {
		// 注册/再次注册监听,前面节点释放锁后,后续节点处理
		if (!zc.exists(lockNodes.get().get(waitNodeIndex.get()))) {
			logger.info("线程注册监听不成功,获取锁:" + Thread.currentThread().getId() + ","
					+ myZnode + "," + waitNode.get() + ",监听结果:空");
			return true;
		}

		logger.info("线程注册监听成功,并等待:" + Thread.currentThread().getId() + ","
				+ myZnode.get() + "," + waitNode.get());
		this.latch.await();
		logger.info("线程被唤醒:" + Thread.currentThread().getId() + ","
				+ myZnode.get() + "," + waitNode.get() + ",超时设置:" + 3);
		return false;
	}

	/**
	 * @throws KeeperException
	 * @throws InterruptedException
	 * 
	 * @return boolean
	 * @throws
	 */
	public boolean getLock() throws InterruptedException, KeeperException {
		if (StringUtils.isBlank(myZnode.get())) {
			if (this.lock()) {
				return true;
			}
		}
		if (!waitForLock("", 3)) {
			return this.getLock();
		}
		return true;
	}

	/**
	 * 释放锁
	 * 
	 * void
	 * 
	 * @throws
	 */
	public void unlock() {
		try {
			// TODO
			int aa = 3 / 0;

			logger.info("释放锁,线程:" + Thread.currentThread().getId() + ",本节点删除:"
					+ myZnode.get());
			logger.info("节点删除前操作:" + Thread.currentThread().getId() + ","
					+ myZnode.get() + "," + zc.readData(myZnode.get()));
			zc.writeData(myZnode.get(), ("本节点要删除:" + Thread.currentThread()
					.getId()).getBytes());
			zc.delete(myZnode.get());// 删除自身节点,并触发后一节点的监听
			logger.info("剩余节点:" + Thread.currentThread().getId() + ","
					+ myZnode.get() + "," + zc.getChildren(root));
		} catch (Exception e) {
			logger.error("解决锁异常,并强制删除:" + Thread.currentThread().getId() + ","
					+ myZnode.get(), e);
			zc.delete(myZnode.get());// 删除自身节点,并触发后一节点的监听
		}
	}

	/**
	 * 订阅，并监听
	 * 
	 * void
	 * 
	 * @throws
	 */
	public void subscribe(DistributedZCLock lock) {
		this.setListener(lock);
		zc.subscribeChildChanges(root, childListener);// 监听子节点
		zc.subscribeDataChanges(root, dataListener);// 监听数据
		zc.subscribeStateChanges(stateListener);// 监听节点
	}

	public void subscribe(DistributedZCLock lock, String path) {
		this.setListener(lock);
		zc.subscribeChildChanges(path, childListener);// 监听子节点
		zc.subscribeDataChanges(path, dataListener);// 监听数据
		// zc.subscribeStateChanges(stateListener);// 监听节点
	}

	/**
	 * 设置监听
	 * 
	 * void
	 * 
	 * @throws
	 */
	public void setListener(final DistributedZCLock lock) {

		childListener = new IZkChildListener() {

			@Override
			public void handleChildChange(String arg0, List<String> arg1)
					throws Exception {
				logger.info("监听子节点变化:" + Thread.currentThread().getId() + ","
						+ arg0 + "," + JSON.toJSONString(arg1));
			}
		};

		dataListener = new IZkDataListener() {

			@Override
			public void handleDataChange(String arg0, Object arg1)
					throws Exception {
				logger.info("监听节点/数据变化:" + Thread.currentThread().getId() + ","
						+ arg0 + "," + new String((byte[]) arg1));
			}

			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				logger.info("监听节点/数据删除:" + Thread.currentThread().getId() + ","
						+ arg0);
				lock.latch.countDown();
			}
		};
		stateListener = new IZkStateListener() {

			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				logger.info("监听节点变更:" + Thread.currentThread().getId() + ","
						+ JSON.toJSONString(state));
				if (state == KeeperState.Disconnected) {
					logger.info("ZK服务停止:" + Thread.currentThread().getId());
				} else if (state == KeeperState.SyncConnected) {
					logger.info("ZK服务创建:" + Thread.currentThread().getId());
					connectedSignal.countDown();
				} else if (state == KeeperState.ConnectedReadOnly) {
					logger.info("ZK服务变化:AAAAAAA:"
							+ Thread.currentThread().getId());
				} else if (state == KeeperState.SaslAuthenticated) {
					logger.info("ZK服务变化:BBBBBBB:"
							+ Thread.currentThread().getId());
				} else if (state == KeeperState.Expired) {
					logger.info("ZK服务变化:CCCCCCC:"
							+ Thread.currentThread().getId());
				} else {
					logger.info("ZK服务变化:未找到对应项"
							+ Thread.currentThread().getId());
				}
			}

			@Override
			public void handleNewSession() throws Exception {
				logger.info("监听节点创建:" + Thread.currentThread().getId());
			}

			@Override
			public void handleSessionEstablishmentError(Throwable error)
					throws Exception {
				logger.error("监听节点异常:" + Thread.currentThread().getId(), error);
				error.printStackTrace();
				System.exit(1);
			}
		};
	}

}
