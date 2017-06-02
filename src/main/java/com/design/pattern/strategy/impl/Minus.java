package com.design.pattern.strategy.impl;

import com.design.pattern.strategy.ICalculator;

/**
 * 
 * @Title: Minus
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 上午10:59:02
 * @Version:1.0
 */
public class Minus implements ICalculator {

	@Override
	public int calculate(String exp) {
		int arrayInt[] = AbstractCalculator.split(exp, "-");
		return arrayInt[0] - arrayInt[1];
	}

}
