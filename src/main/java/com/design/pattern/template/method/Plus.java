package com.design.pattern.template.method;

/**
 * 
 * @Title: Plus
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 上午11:28:37
 * @Version:1.0
 */
public class Plus extends AbstractCalculator {

	@Override
	public int calculate(int num1, int num2) {
		return num1 + num2;
	}

}
