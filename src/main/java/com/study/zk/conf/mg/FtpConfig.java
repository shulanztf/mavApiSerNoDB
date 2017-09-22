package com.study.zk.conf.mg;

import java.io.Serializable;

/**
 * 
 * @Title: FtpConfig
 * @Description:ZooKeeper 笔记(3) 实战应用之【统一配置管理】
 * @see http://www.cnblogs.com/yjmyzz/p/4604947.html
 * @Author: zhaotf
 * @Since:2017年9月22日 下午3:58:26
 * @Version:1.0
 */
public class FtpConfig implements Serializable {
	private static final long serialVersionUID = 1198422496730854972L;

	/**
	 * 端口号
	 */
	private int port;

	/**
	 * ftp主机名或IP
	 */
	private String host;

	/**
	 * 连接用户名
	 */
	private String user;

	/**
	 * 连接密码
	 */
	private String password;

	public FtpConfig() {

	}

	public FtpConfig(int port, String host, String user, String password) {
		this.port = port;
		this.host = host;
		this.user = user;
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return user + "/" + password + "@" + host + ":" + port;
	}
}
