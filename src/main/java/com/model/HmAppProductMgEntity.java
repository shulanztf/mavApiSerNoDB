package com.model;

import java.math.BigDecimal;

/**
 * @Title: Entity
 * @Description: 标的管理表
 * @author geng
 * @date 2016-07-12 16:30:08
 * @version V1.0
 *
 */
// @Entity
// @Table(name = "hm_app_product_mg", schema = "")
// @DynamicUpdate(true)
// @DynamicInsert(true)
@SuppressWarnings("serial")
public class HmAppProductMgEntity implements java.io.Serializable {
	/** id */
	private long id;
	/** 标的名称 */
	private java.lang.String name;
	/** 标的总额 */
	private BigDecimal totalmoney;
	/** 标的期限 */
	private BigDecimal term;
	/** 最低起投 */
	private BigDecimal mininvest;
	/** 标准利率 */
	private BigDecimal normalrate;
	/** 最高利率 */
	private BigDecimal maxrate;
	/** 产品详情 */
	private java.lang.String productinfo;
	/** 推荐测评 */
	private java.lang.String producttest;
	/** 剩余额度 */
	private BigDecimal remainmoney;
	/** 尾巴 */
	private BigDecimal tailmoney;
	/** 类型 */
	private java.lang.String type;
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
	/** 标的状态 0：抢购中 1：已抢完 */
	private java.lang.String status;
	/** 活动加息（%） */
	private BigDecimal activityaddrate;
	/** 新手加息（%） */
	private BigDecimal newhandaddrate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public BigDecimal getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public BigDecimal getMininvest() {
		return mininvest;
	}

	public void setMininvest(BigDecimal mininvest) {
		this.mininvest = mininvest;
	}

	public BigDecimal getNormalrate() {
		return normalrate;
	}

	public void setNormalrate(BigDecimal normalrate) {
		this.normalrate = normalrate;
	}

	public BigDecimal getMaxrate() {
		return maxrate;
	}

	public void setMaxrate(BigDecimal maxrate) {
		this.maxrate = maxrate;
	}

	public java.lang.String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(java.lang.String productinfo) {
		this.productinfo = productinfo;
	}

	public java.lang.String getProducttest() {
		return producttest;
	}

	public void setProducttest(java.lang.String producttest) {
		this.producttest = producttest;
	}

	public BigDecimal getRemainmoney() {
		return remainmoney;
	}

	public void setRemainmoney(BigDecimal remainmoney) {
		this.remainmoney = remainmoney;
	}

	public BigDecimal getTailmoney() {
		return tailmoney;
	}

	public void setTailmoney(BigDecimal tailmoney) {
		this.tailmoney = tailmoney;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
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

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public BigDecimal getActivityaddrate() {
		return activityaddrate;
	}

	public void setActivityaddrate(BigDecimal activityaddrate) {
		this.activityaddrate = activityaddrate;
	}

	public BigDecimal getNewhandaddrate() {
		return newhandaddrate;
	}

	public void setNewhandaddrate(BigDecimal newhandaddrate) {
		this.newhandaddrate = newhandaddrate;
	}

	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String id
	// */
	// @Id
	// @Column(name = "ID", nullable = false)
	// public long getId() {
	// return this.id;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * id
	// */
	// public void setId(long id) {
	// this.id = id;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 标的名称
	// */
	// @Column(name = "NAME", nullable = true)
	// public java.lang.String getName() {
	// return this.name;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 标的名称
	// */
	// public void setName(java.lang.String name) {
	// this.name = name;
	// }
	//
	// /**
	// * 方法: 取得BigDecimal
	// *
	// * @return: BigDecimal 标的总额
	// */
	// @Column(name = "TOTALMONEY", nullable = true)
	// public BigDecimal getTotalmoney() {
	// return this.totalmoney;
	// }
	//
	// /**
	// * 方法: 设置BigDecimal
	// *
	// * @param: BigDecimal
	// * 标的总额
	// */
	// public void setTotalmoney(BigDecimal totalmoney) {
	// this.totalmoney = totalmoney;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String 标的期限
	// */
	// @Column(name = "TERM", nullable = true)
	// public BigDecimal getTerm() {
	// return this.term;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * 标的期限
	// */
	// public void setTerm(BigDecimal term) {
	// this.term = term;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String �?��起投
	// */
	// @Column(name = "MININVEST", nullable = true)
	// public BigDecimal getMininvest() {
	// return this.mininvest;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * �?��起投
	// */
	// public void setMininvest(BigDecimal mininvest) {
	// this.mininvest = mininvest;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String 标准利率
	// */
	// @Column(name = "NORMALRATE", nullable = true)
	// public BigDecimal getNormalrate() {
	// return this.normalrate;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * 标准利率
	// */
	// public void setNormalrate(BigDecimal normalrate) {
	// this.normalrate = normalrate;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String �?��利率
	// */
	// @Column(name = "MAXRATE", nullable = true)
	// public BigDecimal getMaxrate() {
	// return this.maxrate;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * �?��利率
	// */
	// public void setMaxrate(BigDecimal maxrate) {
	// this.maxrate = maxrate;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 产品详情
	// */
	// @Column(name = "PRODUCTINFO", nullable = true)
	// public java.lang.String getProductinfo() {
	// return this.productinfo;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 产品详情
	// */
	// public void setProductinfo(java.lang.String productinfo) {
	// this.productinfo = productinfo;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 推荐测评
	// */
	// @Column(name = "PRODUCTTEST", nullable = true)
	// public java.lang.String getProducttest() {
	// return this.producttest;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 推荐测评
	// */
	// public void setProducttest(java.lang.String producttest) {
	// this.producttest = producttest;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String 剩余额度
	// */
	// @Column(name = "REMAINMONEY", nullable = true)
	// public BigDecimal getRemainmoney() {
	// return this.remainmoney;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * 剩余额度
	// */
	// public void setRemainmoney(BigDecimal remainmoney) {
	// this.remainmoney = remainmoney;
	// }
	//
	// /**
	// * 方法: 取得String
	// *
	// * @return: String 尾巴
	// */
	// @Column(name = "TAILMONEY", nullable = true)
	// public BigDecimal getTailmoney() {
	// return this.tailmoney;
	// }
	//
	// /**
	// * 方法: 设置String
	// *
	// * @param: String
	// * 尾巴
	// */
	// public void setTailmoney(BigDecimal tailmoney) {
	// this.tailmoney = tailmoney;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 类型
	// */
	// @Column(name = "TYPE", nullable = true)
	// public java.lang.String getType() {
	// return this.type;
	// }
	//
	// /**
	// * 方法: 设置java.lang.String
	// *
	// * @param: java.lang.String
	// * 类型
	// */
	// public void setType(java.lang.String type) {
	// this.type = type;
	// }
	//
	// /**
	// * 方法: 取得java.lang.String
	// *
	// * @return: java.lang.String 登录�?
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
	// * 登录�?
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
	// * @return: java.lang.String 更新�?
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
	// * 更新�?
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
	//
	// @Column(name = "STATUS", nullable = true)
	// public java.lang.String getStatus() {
	// return status;
	// }
	//
	// public void setStatus(java.lang.String status) {
	// this.status = status;
	// }
	//
	// /**
	// * 方法: 取得BigDecimal
	// *
	// * @return: BigDecimal 活动加息�?�?
	// */
	// @Column(name = "ACTIVITYADDRATE", nullable = true)
	// public BigDecimal getActivityaddrate() {
	// return activityaddrate;
	// }
	//
	// /**
	// * 方法: 设置BigDecimal
	// *
	// * @param: BigDecimal
	// * 活动加息�?�?
	// */
	// public void setActivityaddrate(BigDecimal activityaddrate) {
	// this.activityaddrate = activityaddrate;
	// }
	//
	// /**
	// * 方法: 取得BigDecimal
	// *
	// * @return: BigDecimal 新手加息�?�?
	// */
	// @Column(name = "NEWHANDADDRATE", nullable = true)
	// public BigDecimal getNewhandaddrate() {
	// return newhandaddrate;
	// }
	//
	// /**
	// * 方法: 设置BigDecimal
	// *
	// * @param: BigDecimal
	// * 新手加息�?�?
	// */
	// public void setNewhandaddrate(BigDecimal newhandaddrate) {
	// this.newhandaddrate = newhandaddrate;
	// }

}
