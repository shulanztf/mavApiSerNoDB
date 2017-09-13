package com.study.nio.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * @Title: CalculatorUtils
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月13日 上午8:11:58
 * @Version:1.0
 */
public class CalculatorUtils {
	private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	public static Object cal(String expression) throws ScriptException {
		return jse.eval(expression);
	}
}
