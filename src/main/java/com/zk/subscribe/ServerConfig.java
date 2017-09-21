package com.zk.subscribe;

/**
 * 
 * @Title: ServerConfig
 * @Description:
 * @see http://blog.csdn.net/zuoanyinxiang/article/details/50937892
 * @Author: zhaotf
 * @Since:2017年9月21日 上午10:07:15
 * @Version:1.0
 */
public class ServerConfig {
	private String dbUrl;
	private String dbPwd;
	private String dbUser;

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	@Override
	public String toString() {
		return "ServerConfig [dbUrl=" + dbUrl + ", dbPwd=" + dbPwd + ", dbUser=" + dbUser + "]";
	}
}
