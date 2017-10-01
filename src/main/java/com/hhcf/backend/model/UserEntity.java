package com.hhcf.backend.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.hhcf.backend.validate.VaildGroup1;

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
	private Long id;
	@NotBlank(message = "用户名称不能为空")
	private String userName;
	@Size(max = 3, min = 1, message = "密码长度大于0，小于4")
	private String password;
	// @Max(value = 10,message="中10a为")
	@DecimalMax(value = "10")
	private Integer age;
	/** 登录者 */
	private java.lang.String insertuser;
	/** 登录日时 */
	@Past
	private java.util.Date inserttime;
	/** 登录者IP */
	private java.lang.String insertip;
	/** 更新者 */
	private java.lang.String updateuser;
	/** 更新日时 */
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatetime;
	/** 更新者IP */
	private java.lang.String updateip;
	/** 删除标识 */
	private java.lang.String deleteflag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

}
