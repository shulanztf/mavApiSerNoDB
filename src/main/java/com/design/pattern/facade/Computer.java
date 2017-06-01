package com.design.pattern.facade;

/**
 * 
 * @Title: Computer
 * @Description:
 * @Author: zhaotf
 * @Since:2017年6月1日 下午5:48:15
 * @Version:1.0
 */
public class Computer {
	private CPU cpu;
	private Memory memory;
	private Disk disk;

	public Computer() {
		cpu = new CPU();
		memory = new Memory();
		disk = new Disk();
	}

	public void startup() {
		System.out.println("start the computer!结构型模式:外观模式（Facade）");
		cpu.startup();
		memory.startup();
		disk.startup();
		System.out.println("start computer finished!结构型模式:外观模式（Facade）");
	}

	public void shutdown() {
		System.out.println("begin to close the computer!结构型模式:外观模式（Facade）");
		cpu.shutdown();
		memory.shutdown();
		disk.shutdown();
		System.out.println("computer closed!结构型模式:外观模式（Facade）");
	}

}
