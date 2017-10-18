package com.study.concurrent.lock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
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
	private static volatile long lastModified = 0L;// 敏感词文件修改版本
	private static CopyOnWriteArrayList<String> words = new CopyOnWriteArrayList<>();// 读写分离词表
	private static ReentrantLock lock = new ReentrantLock();// 随机锁，并且创建condition阻塞队列
	// private static Condition cond = lock.newCondition();

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		while (!"exit".equals(new Scanner(System.in).nextLine())) {
			for (int i = 0; i < 5; i++) {
				es.execute(new Runnable() {

					@Override
					public void run() {
						System.out.println(Thread.currentThread().getId() + ":敏感词检查:" + ZxbBadWordUtil.isContain("测试"));
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
	private static void checkReload() {
		// 采用while，禁用if
		logger.info(Thread.currentThread().getId() + ":加锁前检查:" + words.toString());
		try {
			while (wordfilter.lastModified() > lastModified) {
				if (lock.tryLock()) {
					logger.info(Thread.currentThread().getId() + ":获取了锁……");
					LineIterator lines = FileUtils.lineIterator(wordfilter, "utf-8");
					List<String> tmpList = new ArrayList<String>();
					String[] wordArr = null;
					while (lines.hasNext()) {
						wordArr = StringUtils.split(lines.nextLine(), ",|，| | ");
						if (null == wordArr || wordArr.length <= 0) {
							continue;
						}
						for (String word : wordArr) {
							if (StringUtils.isNotBlank(word)) {
								tmpList.add(StringUtils.trim(word));
							}
						}
					}
					words.clear();// 清空原有词列表
					words.addAll(tmpList); // 替换原词列表，成功后才更新
					lastModified = wordfilter.lastModified();

					logger.info(
							Thread.currentThread().getId() + ":添加后:" + words.toString() + ",最后修改时间:" + lastModified);
				} else {
					logger.info(Thread.currentThread().getId() + ":开始等待");
					// TimeUnit.SECONDS.sleep(1); // 未取到锁，一秒后再试
					Thread.sleep(500);// 未取到锁，暂停后再试
					logger.info(Thread.currentThread().getId() + ":再次获取锁，最后修改时间:" + lastModified);
					checkReload();
				}
				logger.info(Thread.currentThread().getId() + ":结束词列表处理");
			}
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
	 * 检查字符串是否包含敏感词
	 * 
	 * @param content
	 * @return true:有|false:无
	 */
	public static boolean isContain(String content) {
		if (!wordfilter.exists()) {
			return false;
		}
		// 检查文件有无改动,可线程重入
		checkReload();
		for (String word : words) {
			if (StringUtils.containsIgnoreCase(content, word)) {
				return true;
			}
		}
		return false;
	}

}
