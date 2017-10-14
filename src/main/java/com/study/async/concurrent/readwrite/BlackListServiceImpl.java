package com.study.async.concurrent.readwrite;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @ClassName: BlackListServiceImpl
 * @Description:黑名单服务
 * @see http://ifeve.com/java-copy-on-write/
 * @author: zhaotf
 * @date: 2017年10月14日 下午4:20:56
 */
public class BlackListServiceImpl {

	private static CopyOnWriteMap<String, String> blackList = new CopyOnWriteMap<String, String>();

	public static boolean isBlackList(String id) {
		return blackList.get(id) != null;
	}

	/**
	 * 添加黑名单
	 */
	public static void addBlackList(String id) {
		blackList.put(id, id + ":" + Thread.currentThread().getId() + ":"
				+ System.currentTimeMillis());
	}

	/**
	 * 批量添加黑名单
	 */
	public static void addBlackList(Map<String, String> ids) {
		blackList.putAll(ids);
	}

}
