package com.design.pattern.interpreter;

import com.design.pattern.interpreter.impl.Context;

/**
 * 
 * @Title: Expression
 * @Description:行为型模式:解释器模式（Interpreter）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午4:50:51
 * @Version:1.0
 */
public interface Expression {

	public int interpret(Context context);

}
