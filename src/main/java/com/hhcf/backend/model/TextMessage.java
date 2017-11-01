package com.hhcf.backend.model;

import java.io.Serializable;

/**
 * 
 * @Title: TextMessage
 * @Description:
 * @see {@link http://www.cnblogs.com/accumulater/p/6151953.html?utm_source=itdadao&utm_medium=referral}
 * @Author: zhaotf
 * @Since:2017年10月31日 上午8:32:45
 * @Version:1.0
 */
@SuppressWarnings("serial")
public class TextMessage implements Serializable {
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String Content;
	private String MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
