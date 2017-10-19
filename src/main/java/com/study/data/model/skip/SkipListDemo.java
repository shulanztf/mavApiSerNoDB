package com.study.data.model.skip;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @Title: SkipListDemo
 * @Description:
 * @see http://blog.csdn.net/bigtree_3721/article/details/51291974
 * @Author: zhaotf
 * @Since:2017年10月19日 上午7:43:07
 * @Version:1.0
 */
public class SkipListDemo {

	public static void main(String[] args) {
		// SkipListDemo.skipListMapshow();
		SkipListDemo.skipListSetshow();
	}

	/**
	 * map跳表
	 * 
	 * void
	 */
	public static void skipListMapshow() {
		Map<Integer, String> map = new ConcurrentSkipListMap<>();
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			es.execute(new Runnable() {
				AtomicInteger at = new AtomicInteger(Long.valueOf(System.currentTimeMillis()).intValue());

				@Override
				public void run() {
					map.put(at.getAndIncrement(),
							Thread.currentThread().getId() + ":aa1:" + System.currentTimeMillis());
					map.put(at.getAndIncrement(),
							Thread.currentThread().getId() + ":b要b:" + System.currentTimeMillis());
					map.put(at.getAndIncrement(),
							Thread.currentThread().getId() + ":3ed:" + System.currentTimeMillis());
					map.put(at.getAndIncrement(),
							Thread.currentThread().getId() + ":2afe胯:" + System.currentTimeMillis());
				}
			});
		}
		es.shutdown();

		// /输出是有序的，从小到大。 output 1 2 3 23
		for (Integer key : map.keySet()) {
			System.out.println(map.get(key));
		}
	}

	/**
	 * set跳表
	 * 
	 * void
	 */
	public static void skipListSetshow() {
		ExecutorService es = Executors.newCachedThreadPool();
		// Set<Long> mset = new ConcurrentSkipListSet<>();
		// AtomicLong al = new AtomicLong(System.currentTimeMillis());
		// for (int i = 0; i < 5; i++) {
		// es.execute(new Runnable() {
		// @Override
		// public void run() {
		// for (int i = 0; i < 4; i++) {
		// String str = Thread.currentThread().getId() + "" +
		// al.getAndIncrement();
		// System.out.println(str);
		// mset.add(Long.valueOf(str));
		// }
		// }
		// });
		// }
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// // 输出是有序的，从小到大。
		// System.out.println("ConcurrentSkipListSet result=" + mset);

		Set<String> myset = new ConcurrentSkipListSet<>();
		AtomicLong al = new AtomicLong(System.currentTimeMillis());
		for (int i = 0; i < 5; i++) {
			es.execute(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 4; i++) {
						String str = Thread.currentThread().getId() + "." + al.getAndIncrement();
						System.out.println(str);
						myset.add(str);
					}
				}
			});
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 输出是有序的:ConcurrentSkipListSet contains=[Abc, abc, def, fgi]
		System.out.println("ConcurrentSkipListSet contains=" + myset);

		es.shutdown();

	}

}
