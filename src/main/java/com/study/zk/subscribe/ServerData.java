package com.study.zk.subscribe;

/**
 * 
 * @Title: ServerData
 * @Description:
 * @see http://blog.csdn.net/zuoanyinxiang/article/details/50937892
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:19:45
 * @Version:1.0
 */
public class ServerData {
	private String address;
	private Integer id;
	private String name;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ServerData [address=" + address + ", id=" + id + ", name=" + name + "]";
	}
}
