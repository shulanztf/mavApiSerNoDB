package com.design.pattern.command.impl;

import com.design.pattern.command.Command;

/**
 * 
 * @Title: MyCommand
 * @Description:命令，实现了Command接口
 * @Author: zhaotf
 * @Since:2017年6月2日 下午2:58:08
 * @Version:1.0
 */
public class MyCommand implements Command {

	private Receiver receiver;

	public MyCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void exe(String common) {
		receiver.action(common);
	}

}
