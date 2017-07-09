package com.utils;

/**
 * 
 * @Title: MonitorUtil
 * @Description:
 * @Author: zhaotf
 * @Since:2017年5月31日 下午2:33:26
 * @Version:1.0
 */
public class MonitorUtil {
	private static ThreadLocal<Long> tl = new ThreadLocal<Long>();

	public static void start() {
		tl.set(System.currentTimeMillis());
	}

	// 结束时打印耗时
	public static void finish(String methodName) {
		byte[] arr = "hhhaaa".getBytes();
		System.out.write(arr, 0, arr.length);

		long finishTime = System.currentTimeMillis();
		System.out.println(methodName + "方法耗时" + (finishTime - tl.get()) + "ms");
	}

}
