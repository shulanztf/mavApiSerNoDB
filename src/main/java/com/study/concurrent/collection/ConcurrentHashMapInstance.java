package com.study.concurrent.collection;

import java.util.concurrent.ConcurrentHashMap;

import org.testng.log4testng.Logger;

/**
 * 
 * @Title: ConcurrentHashMapInstance
 * @Description:JAVA并发容器
 * @see http://blog.csdn.net/andy_gx/article/details/43496043
 * @Author: zhaotf
 * @Since:2017年10月13日 上午8:28:26
 * @Version:1.0
 */
public class ConcurrentHashMapInstance {
	private static Logger logger = Logger.getLogger(ConcurrentHashMapInstance.class);
	private static ConcurrentHashMap<?, ?> chm;

	/**
	 * 获取ConcurrentHashMap的全局对象
	 * 
	 * @return ConcurrentHashMap<?,?>
	 */
	public synchronized static ConcurrentHashMap<?, ?> getConMap() {
		if (chm == null) {
			chm = new ConcurrentHashMap<String, Object>();
		}
		return chm;
	}
}
