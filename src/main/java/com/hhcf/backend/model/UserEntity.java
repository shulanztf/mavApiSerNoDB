package com.hhcf.backend.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @Title: UserEntity
 * @Description: 用户表
 * @Author:Administrator
 * @Since:2016年11月17日 上午10:14:40
 * @Version:1.1.0
 */
// @Entity
// @Table(name = "t_user_mg", schema = "")
// @DynamicUpdate(true)
// @DynamicInsert(true)
@XmlRootElement
@SuppressWarnings("serial")
public class UserEntity implements java.io.Serializable {
	private long id;
	private String userName;
	private String password;
	/** 登录者 */
	private java.lang.String insertuser;
	/** 登录日时 */
	private java.util.Date inserttime;
	/** 登录者IP */
	private java.lang.String insertip;
	/** 更新者 */
	private java.lang.String updateuser;
	/** 更新日时 */
	private java.util.Date updatetime;
	/** 更新者IP */
	private java.lang.String updateip;
	/** 删除标识 */
	private java.lang.String deleteflag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.lang.String getInsertuser() {
		return insertuser;
	}

	public void setInsertuser(java.lang.String insertuser) {
		this.insertuser = insertuser;
	}

	public java.util.Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(java.util.Date inserttime) {
		this.inserttime = inserttime;
	}

	public java.lang.String getInsertip() {
		return insertip;
	}

	public void setInsertip(java.lang.String insertip) {
		this.insertip = insertip;
	}

	public java.lang.String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(java.lang.String updateuser) {
		this.updateuser = updateuser;
	}

	public java.util.Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	public java.lang.String getUpdateip() {
		return updateip;
	}

	public void setUpdateip(java.lang.String updateip) {
		this.updateip = updateip;
	}

	public java.lang.String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(java.lang.String deleteflag) {
		this.deleteflag = deleteflag;
	}

	//
	// @Id
	// @Column(name = "ID", nullable = false)
	// public long getId() {
	// return id;
	// }
	//
	// public void setId(long id) {
	// this.id = id;
	// }
	//
	// @Column(name = "USERNAME", nullable = false)
	// public String getUserName() {
	// return userName;
	// }
	//
	// public void setUserName(String userName) {
	// this.userName = userName;
	// }
	//
	// @Column(name = "PASSWORD")
	// public String getPassword() {
	// return password;
	// }
	//
	// public void setPassword(String password) {
	// this.password = password;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 登录者
	// */
	// @Column(name = "INSERTUSER", nullable = true)
	// public java.lang.String getInsertuser() {
	// return this.insertuser;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 登录者
	// */
	// public void setInsertuser(java.lang.String insertuser) {
	// this.insertuser = insertuser;
	// }
	//
	// /**
	// * 方法: 取得java.util.Date
	// *
	// * @return: java.util.Date 登录日时
	// */
	// @Column(name = "INSERTTIME", nullable = true)
	// public java.util.Date getInserttime() {
	// return this.inserttime;
	// }
	//
	// /**
	// * 方法: 设置java.util.Date
	// *
	// * @param: java.util.Date
	// * 登录日时
	// */
	// public void setInserttime(java.util.Date inserttime) {
	// this.inserttime = inserttime;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 登录者IP
	// */
	// @Column(name = "INSERTIP", nullable = true)
	// public java.lang.String getInsertip() {
	// return this.insertip;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 登录者IP
	// */
	// public void setInsertip(java.lang.String insertip) {
	// this.insertip = insertip;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 更新者
	// */
	// @Column(name = "UPDATEUSER", nullable = true)
	// public java.lang.String getUpdateuser() {
	// return this.updateuser;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 更新者
	// */
	// public void setUpdateuser(java.lang.String updateuser) {
	// this.updateuser = updateuser;
	// }
	//
	// /**
	// * 方法: 取得java.util.Date
	// *
	// * @return: java.util.Date 更新日时
	// */
	// @Column(name = "UPDATETIME", nullable = true)
	// public java.util.Date getUpdatetime() {
	// return this.updatetime;
	// }
	//
	// /**
	// * 方法: 设置java.util.Date
	// *
	// * @param: java.util.Date
	// * 更新日时
	// */
	// public void setUpdatetime(java.util.Date updatetime) {
	// this.updatetime = updatetime;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 更新者IP
	// */
	// @Column(name = "UPDATEIP", nullable = true)
	// public java.lang.String getUpdateip() {
	// return this.updateip;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 更新者IP
	// */
	// public void setUpdateip(java.lang.String updateip) {
	// this.updateip = updateip;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 删除标识
	// */
	// @Column(name = "DELETEFLAG", nullable = true)
	// public java.lang.String getDeleteflag() {
	// return this.deleteflag;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 删除标识
	// */
	// public void setDeleteflag(java.lang.String deleteflag) {
	// this.deleteflag = deleteflag;
	// }
}
