package com.model;

import java.util.Date;

/**
 * 数据转换测试类
 * 
 * @author zhaotf 2017年6月17日 下午3:28:18
 *
 */
public class TestModel {
	private int age;
	private Date birth;
	private String name;
	private boolean good;
	private long times;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

}
