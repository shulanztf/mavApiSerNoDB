package com.proxy;

/**
 * 
 * @Title: Student
 * @Description:JAVA代理
 * @Author: zhaotf
 * @Since:2017年5月31日 下午2:20:57
 * @Version:1.0
 */
public class Student implements Person {
	private String name;

	public Student(String name) {
		this.name = name;
	}

	@Override
	public void giveMoney() {
		// TODO Auto-generated method stub
		// System.out.println(name + "活动abc.");
		try {
			// 假设数钱花了一秒时间
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + "上交班费50元");
	}

}
