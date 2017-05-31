package com.tuil;

/**
 * 
 * @author yoUng
 * 
 * @description 发送http请求
 * 
 * @filename HttpUtil.java
 * 
 * @time 2011-6-15 下午05:26:36
 * 
 * @version 1.0
 * 
 */
public class HttpUtil {

	// 定义一个本地方法
	public native void sayHello();

	public static void main(String[] args) {
		// 调用动态链接库
		System.loadLibrary("HttpUtil");
		HttpUtil jniDemo = new HttpUtil();
		jniDemo.sayHello();
	}

}
