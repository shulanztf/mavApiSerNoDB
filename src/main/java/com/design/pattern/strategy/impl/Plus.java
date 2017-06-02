package com.design.pattern.strategy.impl;

import com.design.pattern.strategy.ICalculator;

/**
 * 
 * @Title: Plus
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 上午10:58:23
 * @Version:1.0
 */
public class Plus implements ICalculator {

	@Override
	public int calculate(String exp) {
		int arrayInt[] = AbstractCalculator.split(exp, "\\+");
		return arrayInt[0] + arrayInt[1];
	}

}
