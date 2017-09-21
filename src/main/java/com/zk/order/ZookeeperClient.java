package com.zk.order;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * 
 * @Title: ZookeeperClient
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:48:50
 * @Version:1.0
 */
public class ZookeeperClient {
	public static final String LOCK_ZNODE = "/zook_lock";
	public static final String CONNECT_URL = "192.168.159.131:2183";
	public static final String INIT_VAL = "10000";
	public static CuratorFrameworkFactory.Builder builder;
	private Object lock = new Object();

	public ZookeeperClient() {
	}

	public CuratorFrameworkFactory.Builder getZookeeperInstance() {
		if (builder == null) {
			synchronized (lock) {
				builder = CuratorFrameworkFactory.builder().connectionTimeoutMs(1000).connectString(CONNECT_URL)
						.defaultData("0".getBytes())
						// .namespace("curator")
						.retryPolicy(new ExponentialBackoffRetry(3000, 3)).maxCloseWaitMs(5000)
						.threadFactory(new ThreadFactory() {
							public final AtomicInteger counter = new AtomicInteger(0);

							public Thread newThread(Runnable r) {
								Thread thread = new Thread(r, "curator-" + counter.getAndIncrement());
								thread.setDaemon(true);
								return thread;
							}
						});
			}
		}

		return builder;
	}

	public static String getSequence() {
		CuratorFramework client = new ZookeeperClient().getZookeeperInstance().build();
		client.start();
		InterProcessMutex lock = new InterProcessMutex(client, LOCK_ZNODE);
		try {
			boolean retry = true;
			byte[] newData = null;
			do {
				if (lock.acquire(1000, TimeUnit.MILLISECONDS)) {
					byte[] oldData = client.getData().forPath(LOCK_ZNODE);
					String s = new String(oldData);
					if ("".equals(s)) {
						s = INIT_VAL;
					}
					int d = Integer.parseInt(s);
					d = d + 1;
					s = String.valueOf(d);
					newData = s.getBytes();
					client.setData().forPath(LOCK_ZNODE, newData);
					retry = false;
				}
			} while (retry);

			return new String(newData);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (lock.isAcquiredInThisProcess()) {
				try {
					lock.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			CloseableUtils.closeQuietly(client);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getSequence());
	}
}
