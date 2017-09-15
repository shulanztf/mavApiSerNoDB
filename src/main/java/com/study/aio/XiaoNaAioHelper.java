package com.study.aio;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Title: XiaoNaAioHelper
 * @Description:工具类
 * @see http://blog.csdn.net/zmx729618/article/details/53170867
 * @Author: zhaotf
 * @Since:2017年9月15日 上午7:44:20
 * @Version:1.0
 */
public class XiaoNaAioHelper {
	private static BlockingQueue<String> words;
	private static Random random;

	public XiaoNaAioHelper() throws InterruptedException {
		words = new ArrayBlockingQueue<String>(5);
		words.put("hi");
		words.put("who");
		words.put("what");
		words.put("where");
		words.put("bye");

		random = new Random();
	}

	public String getWord() {
		return words.poll();
	}

	public void sleep() {
		try {
			TimeUnit.SECONDS.sleep(random.nextInt(3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep(long l) {
		try {
			TimeUnit.SECONDS.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取key对应的内容
	 * 
	 * @param key 问题/KEY
	 * @return String
	 */
	public static String getAnswer(String key) {
		String answer = null;
		switch (key) {
		case "who":
			answer = "我是小娜\n";
			break;
		case "what":
			answer = "我是来帮你解闷的\n";
			break;
		case "where":
			answer = "我来自外太空\n";
			break;
		case "hi":
			answer = "hello\n";
			break;
		case "bye":
			answer = "88\n";
			break;
		default:
			answer = "请输入 who， 或者what， 或者where";
		}
		return answer;
	}

}
