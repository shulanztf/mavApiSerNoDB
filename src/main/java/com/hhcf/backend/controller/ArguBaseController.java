package com.hhcf.backend.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.hhcf.annotation.HmForm;
import com.hhcf.annotation.ZxbForm;
import com.hhcf.backend.model.HmUserEntity;
import com.hhcf.backend.model.ZxbUserEntity;

/**
 * 
 * @Title: ArguBaseController
 * @Description:自定义解析器实现请求参数绑定方法参数
 * @see http://blog.csdn.net/truong/article/details/30971317
 * @Author: zhaotf
 * @Since:2017年9月30日 下午3:50:08
 * @Version:1.0
 */
@Controller
@RequestMapping("/arguBase")
public class ArguBaseController {
	private static Logger logger = Logger.getLogger(ArguBaseController.class);

	/**
	 * 
	 * @see http://127.0.0.1:8380/mavApiSerNoDB/arguBase/testArgu.do
	 * @see aa
	 */
	@RequestMapping(value = "/testArgu", method = RequestMethod.POST)
	public Object testArgu(@HmForm("hm") HmUserEntity hmUser, @HmForm("zxb") ZxbUserEntity zxbUser) {
		logger.info("HM参数:" + JSON.toJSONString(hmUser));
		logger.info("ZXB参数:" + JSON.toJSONString(zxbUser));
		return new Date();
	}

	/**
	 * @see http://127.0.0.1:8380/mavApiSerNoDB/arguBase/testParams.do
	 */
	@RequestMapping(value = "/testParams", method = RequestMethod.POST)
	public String testParams(HmUserEntity hmUser) {
		logger.info("HM参数:" + JSON.toJSONString(hmUser));
		// logger.info("ZXB参数:" + JSON.toJSONString(zxbUser));
		return new Date().toString();
	}

}
