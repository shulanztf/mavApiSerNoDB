package com.hhcf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: TSUser
 * @Description:
 * @Author: zhaotf
 * @Since:2017年12月11日 下午3:27:22
 */
public class TSUser implements Serializable {
	private static final long serialVersionUID = -5410691946720084407L;

	private Long id;
	private String usercode;
	private String username;
	private Date regtime;
	private int age;
	private String addres;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
