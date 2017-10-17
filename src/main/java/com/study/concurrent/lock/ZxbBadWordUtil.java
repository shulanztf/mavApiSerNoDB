package com.study.concurrent.lock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @Title: ZxbBadWordUtil
 * @Description:
 * @Author: zhaotf
 * @Since:2017年10月17日 下午4:33:37
 * @Version:1.0
 */
public class ZxbBadWordUtil {
	private static Logger logger = Logger.getLogger(ZxbBadWordUtil.class);
	private final static File wordfilter = new File("/data/hmlc/workFile.txt");
	private static volatile long lastModified = 0L;
	private static List<String> words = new ArrayList<String>();
	private static ReentrantLock lock = new ReentrantLock();// 随机锁，并且创建condition阻塞队列
	// private static Condition cond = lock.newCondition();

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		while (!"exit".equals(new Scanner(System.in).nextLine())) {
			for (int i = 0; i < 5; i++) {
				es.execute(new Runnable() {

					@Override
					public void run() {
						ZxbBadWordUtil.checkReloadConcurrent();
					}

				});
			}
		}
		es.shutdown();
		System.out.println("执行结束");
	}

	/**
	 * 检查文件有无改动,可线程重入 TODO
	 */
	private static void checkReloadConcurrent() {
		// 采用while，禁用if
		try {
			System.out.println(Thread.currentThread().getId() + ":添加前:" + words.toString());
			while (wordfilter.lastModified() > lastModified) {
				logger.info(Thread.currentThread().getId() + ":最后修改时间:" + lastModified);
				if (lock.tryLock()) {
					logger.info(Thread.currentThread().getId() + ":获取了锁……");
					words.clear();// 清空原有词列表
					LineIterator lines = FileUtils.lineIterator(wordfilter, "utf-8");
					String[] wordArr = null;
					while (lines.hasNext()) {
						wordArr = StringUtils.split(lines.nextLine(), ",|，| | ");
						if (null == wordArr || wordArr.length <= 0) {
							continue;
						}
						for (String word : wordArr) {
							if (StringUtils.isNotBlank(word)) {
								words.add(StringUtils.trim(word));
							}
						}
					}
					lastModified = wordfilter.lastModified();
					System.out.println(
							Thread.currentThread().getId() + ":添加后:" + words.toString() + ",最后修改时间:" + lastModified);
				} else {
					logger.info(Thread.currentThread().getId() + ":开始等待");
					// TimeUnit.SECONDS.sleep(1); // 未取到锁，一秒后再试
					Thread.sleep(500);// 未取到锁，暂停后再试
					logger.info(Thread.currentThread().getId() + ":再次获取锁，最后修改时间:" + lastModified);
					checkReloadConcurrent();
				}
			}
			logger.info(Thread.currentThread().getId() + ":结束词列表处理");
		} catch (Exception e) {
			logger.error(Thread.currentThread().getId() + ":设置词列表异常", e);
		} finally {
			if (lock.isHeldByCurrentThread()) {
				logger.info(Thread.currentThread().getId() + ":释放锁");
				lock.unlock();// 释放锁是必须的
			}
		}
	}

	/**
	 * 检查文件有无改动 TODO
	 */
	private static void checkReload() {
		if (wordfilter.lastModified() > lastModified) {
			synchronized (ZxbBadWordUtil.class) {
				try {
					lastModified = wordfilter.lastModified();
					LineIterator lines = FileUtils.lineIterator(wordfilter, "utf-8");
					String[] wordArr = null;
					while (lines.hasNext()) {
						wordArr = StringUtils.split(lines.nextLine(), ",|，| | ");
						if (null == wordArr || wordArr.length <= 0) {
							continue;
						}
						for (String word : wordArr) {
							if (StringUtils.isNotBlank(word)) {
								words.add(StringUtils.trim(word));
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
