package com.design.pattern.command;

/**
 * 
 * @Title: Command
 * @Description: 行为型模式:命令模式（Command）
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:57:24
 * @Version:1.0
 */
public interface Command {

	/**
	 * 指令传递
	 * 
	 * @param common
	 *            指令 void
	 */
	public void exe(String common);
}
