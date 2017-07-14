package com.model;

import java.math.BigDecimal;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 
 * @Title: ZxbMoneyInRecModel
 * @Description:
 * @Author: zhaotf
 * @Since:2017年7月14日 下午2:07:12
 * @Version:1.0
 */
public class ZxbMoneyInRecModel {
	/** 资金入账ID */
	@Excel(name = "资金入账ID", width = 50)
	private java.lang.String id;
	/** 用户id */
	@Excel(name = "用户ID", width = 50)
	private java.lang.String userid;
	/** 资产ID */
	@Excel(name = "资产ID", width = 50)
	private java.lang.String assetid;
	/** 手机号 */
	@Excel(name = "手机号", width = 50)
	private java.lang.String mobileno;
	/** 日期时间 */
	@Excel(name = "日期时间", width = 50)
	private java.util.Date rectime;
	/** 产品分类 */
	@Excel(name = "产品分类", width = 50, replace = { " _null", "短乐帮_A", "长隆帮_B" })
	private java.lang.String producttype;
	/** 期限 */
	@Excel(name = "期限", width = 50)
	private BigDecimal term;
	/** 实际支付金额 */
	@Excel(name = "实际支付金额", width = 50)
	private BigDecimal paymoney;
	/** 抵扣金额 */
	@Excel(name = "抵扣金额", width = 50)
	private BigDecimal deductionmoney;
	/** 资金区分 */
	@Excel(name = "资金区分", width = 50)
	private java.lang.String moneyclass;
	/** 银行code */
	@Excel(name = "银行code", width = 50)
	private java.lang.String bankcode;
	/** 银行卡号 */
	@Excel(name = "银行卡号", width = 50)
	private java.lang.String bankcardnum;
	/** 第三方支付Code */
	@Excel(name = "第三方支付code", width = 50)
	private java.lang.String thirdpaycode;
	/** 设备来源 */
	@Excel(name = "设备来源", width = 50)
	private java.lang.String fromdevice;
	/** 渠道来源 */
	@Excel(name = "渠道来源", width = 50)
	private java.lang.String fromchannel;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getAssetid() {
		return assetid;
	}

	public void setAssetid(java.lang.String assetid) {
		this.assetid = assetid;
	}

	public java.lang.String getMobileno() {
		return mobileno;
	}

	public void setMobileno(java.lang.String mobileno) {
		this.mobileno = mobileno;
	}

	public java.util.Date getRectime() {
		return rectime;
	}

	public void setRectime(java.util.Date rectime) {
		this.rectime = rectime;
	}

	public java.lang.String getProducttype() {
		return producttype;
	}

	public void setProducttype(java.lang.String producttype) {
		this.producttype = producttype;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public BigDecimal getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}

	public BigDecimal getDeductionmoney() {
		return deductionmoney;
	}

	public void setDeductionmoney(BigDecimal deductionmoney) {
		this.deductionmoney = deductionmoney;
	}

	public java.lang.String getMoneyclass() {
		return moneyclass;
	}

	public void setMoneyclass(java.lang.String moneyclass) {
		this.moneyclass = moneyclass;
	}

	public java.lang.String getBankcode() {
		return bankcode;
	}

	public void setBankcode(java.lang.String bankcode) {
		this.bankcode = bankcode;
	}

	public java.lang.String getBankcardnum() {
		return bankcardnum;
	}

	public void setBankcardnum(java.lang.String bankcardnum) {
		this.bankcardnum = bankcardnum;
	}

	public java.lang.String getThirdpaycode() {
		return thirdpaycode;
	}

	public void setThirdpaycode(java.lang.String thirdpaycode) {
		this.thirdpaycode = thirdpaycode;
	}

	public java.lang.String getFromdevice() {
		return fromdevice;
	}

	public void setFromdevice(java.lang.String fromdevice) {
		this.fromdevice = fromdevice;
	}

	public java.lang.String getFromchannel() {
		return fromchannel;
	}

	public void setFromchannel(java.lang.String fromchannel) {
		this.fromchannel = fromchannel;
	}

}
