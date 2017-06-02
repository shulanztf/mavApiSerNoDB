package com.design.pattern.strategy.impl;

/**
 * 
 * @Title: AbstractCalculator
 * @Description:行为型模式:策略模式（strategy）: 辅助类
 * @Author: zhaotf
 * @Since:2017年6月2日 上午10:57:52
 * @Version:1.0
 */
public class AbstractCalculator {

	public static int[] split(String exp, String opt) {
		String array[] = exp.split(opt);
		int arrayInt[] = new int[2];
		arrayInt[0] = Integer.parseInt(array[0]);
		arrayInt[1] = Integer.parseInt(array[1]);
		return arrayInt;
	}
}
