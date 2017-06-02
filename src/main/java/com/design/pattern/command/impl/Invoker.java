package com.design.pattern.command.impl;

import com.design.pattern.command.Command;

/**
 * 
 * @Title: Invoker
 * @Description:调用者（司令员）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:59:19
 * @Version:1.0
 */
public class Invoker {
	private Command command;

	public Invoker(Command command) {
		this.command = command;
	}

	/**
	 * 调用其它执行方
	 * 
	 * @param common
	 *            指令参数 void
	 */
	public void action(String common) {
		command.exe(common);
	}
}
