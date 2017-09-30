package com.hhcf.backend.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: HmUserEntity
 * @Description:springMVC自定义解析器实现请求参数绑定方法参数
 * @see http://blog.csdn.net/truong/article/details/30971317
 * @Author: zhaotf
 * @Since:2017年9月30日 下午3:02:32
 * @Version:1.0
 */
@SuppressWarnings("serial")
public class HmUserEntity implements Serializable {
	private long id;
	private String userName;
	private String password;
	private Integer age;
	private Date bathed;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBathed() {
		return bathed;
	}

	public void setBathed(Date bathed) {
		this.bathed = bathed;
	}

}
