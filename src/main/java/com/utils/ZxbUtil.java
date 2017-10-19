package com.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 真心帮工具类
 * 
 * @author geng
 *
 */
public class ZxbUtil {
	public static final String CHECK_PASSWORD_REG = "^([a-zA-Z0-9]){6,20}$"; // 密码的正则表达式
	public static final String CHECK_PASSWORD_REG_LENG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$"; // 密码格式,8~20位，数字、字母组合
	public static final String MOBILE_REG = "^[1][3,4,5,7,8][0-9]{9}$"; // 手机号码正则表达式
	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString().replace("-", "");
		return uuidStr;
	}

	/**
	 * IP取得
	 */
	public static String getIp() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress().toString();
	}

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		// ConcurrentSkipListSet<String> skipSet = new
		// ConcurrentSkipListSet<>();
		for (int i = 0; i < 5; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 4; i++) {
						String prel = Thread.currentThread().getId() + ":" + i;
						System.out.println("默认订单号:" + getOrderNumber(prel) + "-" + getOrderNumber(prel, 30));
					}
				}
			});
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		es.shutdown();
	}

	// 订单号计算器，原子操作 TODO
	private static AtomicLong orderCounter = new AtomicLong(System.currentTimeMillis());

	/**
	 * 获取订单号 用户的邀请码 + 当前时间的毫秒数，20位 ,毫秒数 保留后几位
	 * 
	 * @param preludeCode
	 *            邀请码
	 * @return
	 */
	public static String getOrderNumber(String preludeCode) {
		int length = 20;
		// return getOrderNumber(preludeCode, length);
		// String code = String.valueOf(System.currentTimeMillis());
		String code = String.valueOf(orderCounter.getAndIncrement());
		if (StringUtils.length(preludeCode) + code.length() > length) {
			code = StringUtils.substring(code, code.length() - (length - preludeCode.length()));
		}
		return preludeCode + code;
	}

	/**
	 * 设置流水码
	 * 
	 * @param preludeCode
	 *            前缀
	 * @param length
	 *            流水码长度
	 * @return String
	 */
	public static String getOrderNumber(String preludeCode, int length) {
		// String code = String.valueOf(System.currentTimeMillis());
		String code = String.valueOf(orderCounter.getAndIncrement());
		if (StringUtils.length(preludeCode) + code.length() > length) {
			code = StringUtils.substring(code, code.length() - (length - preludeCode.length()));
		}
		return preludeCode + code;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return true:是 | false:不是
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 产生指定范围内的随机数
	 * 
	 * @param num1
	 *            数值1
	 * @param num2
	 *            数值2
	 * @return
	 */
	public static String createRandomNum(double num1, double num2) {
		if (num1 >= num2) {
			double dbNum = Math.random() * (num1 - num2) + num2;
			return String.format("%.2f", dbNum);
		} else {
			double dbNum = Math.random() * (num2 - num1) + num1;
			return String.format("%.2f", dbNum);
		}
	}

	/**
	 * 必须是数字类型，且大于等于0 如果含有小数，小数位最多2位
	 * 
	 * @param str
	 * @return true 合法 false 不合法
	 */
	public static boolean validMoney(String str) {
		String reg = "^(([0-9]+\\d*)|([0-9]+\\d*\\.\\d{1,2}))$";
		return Pattern.compile(reg).matcher(str).find();
	}

	/**
	 * 匹配非负整数（正整数 + 0）
	 * 
	 * @param str
	 * @return true 合法 false 不合法
	 */
	public static boolean validPositiveInteger(String str) {
		String reg = "^\\d+$";
		return Pattern.compile(reg).matcher(str).find();
	}

	/**
	 * 是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}
