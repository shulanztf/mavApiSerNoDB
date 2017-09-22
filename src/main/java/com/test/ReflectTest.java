package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.annotation.Reflect;
import com.annotation.processor.ReflectProcessor;
import com.study.zk.lock.DistributedLock;

/**
 * @ClassName: ReflectTest
 * @Description: TODO JAVA 自定义注解
 * @author zhaotf
 * @date 2017年7月9日 下午3:04:32
 */
public class ReflectTest {

	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(10);// 线程池
		final Integer ige = 0;
		for (int i = 0; i < 3; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("aaaaaaaaaaa:" + ige);
				}
			});
		}
		es.shutdown();
	}

}
