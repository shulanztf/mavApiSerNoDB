package com.hhcf.backend.model;

import java.math.BigDecimal;

import org.ztfframework.poi.excel.annotation.ZtfExcel;

/**
 * 
 * @ClassName: ZxbMoneyOutRecModel
 * @Description: ZtfExcel导出
 * @author zhaotf
 * @date 2017年7月15日 下午12:27:43
 */
public class ZxbMoneyOutRecModel {
	/** 资金入账ID */
	@ZtfExcel(name = "资金入账ID", width = 50)
	private java.lang.String id;
	private java.lang.String userid;
	/** 资产ID */
	@ZtfExcel(name = "资产ID", width = 50)
	private java.lang.String assetid;
	/** 手机号 */
	@ZtfExcel(name = "手机号", width = 50)
	private java.lang.String mobileno;
	/** 日期时间 */
	@ZtfExcel(name = "日期时间", width = 50)
	private java.util.Date rectime;
	/** 产品分类 */
	// @ZtfExcel(name = "产品分类", width = 50, replace = { " _null", "短乐帮_A",
	// "长隆帮_B" })
	@ZtfExcel(name = "产品分类", width = 50, replaceCode = "producttype")
	private java.lang.String producttype;
	/** 实际支付金额 */
	@ZtfExcel(name = "实际支付金额", width = 50)
	private BigDecimal paymoney;
	/** 抵扣金额 */
	@ZtfExcel(name = "抵扣金额", width = 50)
	private BigDecimal deductionmoney;
	/** 资金区分 */
	// @ZtfExcel(name = "资金区分", width = 50)
	@ZtfExcel(name = "资金区分", width = 50, replaceCode = "moneyinclass")
	private java.lang.String moneyclass;

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

	// public BigDecimal getTerm() {
	// return term;
	// }
	//
	// public void setTerm(BigDecimal term) {
	// this.term = term;
	// }

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

	// public java.lang.String getBankcode() {
	// return bankcode;
	// }
	//
	// public void setBankcode(java.lang.String bankcode) {
	// this.bankcode = bankcode;
	// }
	//
	// public java.lang.String getBankcardnum() {
	// return bankcardnum;
	// }
	//
	// public void setBankcardnum(java.lang.String bankcardnum) {
	// this.bankcardnum = bankcardnum;
	// }
	//
	// public java.lang.String getThirdpaycode() {
	// return thirdpaycode;
	// }
	//
	// public void setThirdpaycode(java.lang.String thirdpaycode) {
	// this.thirdpaycode = thirdpaycode;
	// }
	//
	// public java.lang.String getFromdevice() {
	// return fromdevice;
	// }
	//
	// public void setFromdevice(java.lang.String fromdevice) {
	// this.fromdevice = fromdevice;
	// }
	//
	// public java.lang.String getFromchannel() {
	// return fromchannel;
	// }
	//
	// public void setFromchannel(java.lang.String fromchannel) {
	// this.fromchannel = fromchannel;
	// }

}
