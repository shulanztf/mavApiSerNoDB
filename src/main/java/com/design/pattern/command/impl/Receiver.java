package com.design.pattern.command.impl;

/**
 * 
 * @Title: Receiver
 * @Description:被调用者（士兵）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:59:00
 * @Version:1.0
 */
public class Receiver {

	/**
	 * 对外接口
	 * 
	 * @param common
	 *            指令 void
	 */
	public void action(String common) {
		System.out.println("command received!被调用者，指令：" + common);
	}
}
