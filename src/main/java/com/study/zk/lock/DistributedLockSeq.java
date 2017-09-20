package com.study.zk.lock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @Title: DistributedLockSeq
 * @Description: 依赖Zookeeper生成全局唯一序列号
 * @see http://www.bubuko.com/infodetail-1048560.html
 * @Author: zhaotf
 * @Since:2017年9月18日 上午9:49:21
 * @Version:1.0
 */
public class DistributedLockSeq {

	public static final String LOCK_ZNODE = "/lock";

	public static CuratorFrameworkFactory.Builder builder;

	static {
		builder = CuratorFrameworkFactory.builder().connectionTimeoutMs(1000)
				.connectString("192.168.159.131:2181,192.168.159.131:2182,192.168.159.131:2183")
				.defaultData("0".getBytes()).namespace("curator").retryPolicy(new ExponentialBackoffRetry(3000, 3))
				.maxCloseWaitMs(5000).threadFactory(new ThreadFactory() {

					public final AtomicInteger counter = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r, "curator-" + counter.getAndIncrement());
						thread.setDaemon(true);
						return thread;
					}
				});
	}

	public static void main(String[] args) {
		final ExecutorService service = Executors.newFixedThreadPool(20);

		for (int i = 0; i < 20; i++) {
			service.execute(new SeqTask("[Concurrent-" + i + "]"));
		}

		if (!service.isShutdown()) {
			try {
				service.shutdown();
				if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
					service.shutdownNow();
				}
			} catch (InterruptedException e) {
				service.shutdownNow();
				System.out.println(e.getMessage());
			}
		}
	}

	// 借助curatorFramework利用Zookeeper实现分布式seq生成
	public static class SeqTask implements Runnable {

		private final String seqTaskName;

		public SeqTask(String seqTaskName) {
			this.seqTaskName = seqTaskName;
		}

		@Override
		public void run() {
			CuratorFramework client = builder.build();
			client.start();
			InterProcessMutex lock = new InterProcessMutex(client, LOCK_ZNODE);
			try {
				boolean retry = true;
				do {
					if (lock.acquire(1000, TimeUnit.MILLISECONDS)) {
						Stat stat = new Stat();
						byte[] oldData = client.getData().storingStatIn(stat).forPath(LOCK_ZNODE);
						String str = new String(oldData);
						int d = Integer.parseInt(str);
						d = d + 1;
						str = String.valueOf(d);
						byte[] newData = str.getBytes();
						client.setData().forPath(LOCK_ZNODE, newData);
						retry = false;
						System.out.println(seqTaskName + " obtain seq :" + new String(newData));
					}
				} while (retry);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (lock.isAcquiredInThisProcess()) {
						lock.release();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					CloseableUtils.closeQuietly(client);
				}
			}
		}
	}

}
