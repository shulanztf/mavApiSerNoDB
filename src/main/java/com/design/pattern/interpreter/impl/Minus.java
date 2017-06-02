package com.design.pattern.interpreter.impl;

import com.design.pattern.interpreter.Expression;

/**
 * 
 * @Title: Minus
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:54:03
 * @Version:1.0
 */
public class Minus implements Expression {

	@Override
	public int interpret(Context context) {
		return context.getNum1() - context.getNum2();
	}

}
