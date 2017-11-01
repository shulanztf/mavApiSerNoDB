package com.hhcf.backend.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Title: WXWechatService
 * @Description:
 * @Author: zhaotf
 * @Since:2017年10月31日 上午8:22:05
 * @Version:1.0
 */
public interface WXWechatService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return String
	 */
	public String weixinPost(HttpServletRequest request);

}
