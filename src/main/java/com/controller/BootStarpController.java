package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.model.HmAppProductMgEntity;

import net.sf.json.JSONObject;

/**
 * 
 * @Title: BootStarpController
 * @Description: BootStarp学习
 * @Author:Administrator
 * @Since:2016年9月12日 上午10:46:21
 * @Version:1.1.0
 */
@Controller
@RequestMapping("/bootStarpController")
public class BootStarpController {
	private static final Logger logger = Logger.getLogger(BootStarpController.class);

	/**
	 * @category nginx反向代理
	 * @see http://localhost:8080/mav-api-ser/bootStarpController/
	 *      nginxReversedProxy.do
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/nginxReversedProxy")
	public String nginxReversedProxy(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("bs_test", "服务器返回数据");
		} catch (Exception e) {
			logger.error(e);
		}
		return "views/nginxTest1";
	}

	/**
	 * @see http://localhost:8080/mav-api-ser/bootStarpController/toLogin.do
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("bs_test", "学习");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "bootstarp/login_bs";
	}

	/**
	 * http://localhost:8080/mav-api-ser/bootStarpController/validateLogin.do
	 * 
	 * @return
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "/validateLogin", method = RequestMethod.POST)
	public Object validateLogin(HttpServletRequest request, HttpServletResponse response, Model model, String loginObj,
			String accountNo, String pwd) {
		JSONObject result = new JSONObject();
		try {
			result.put("total", 11);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 登录验证通过后，跳转至主页
	 * 
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "successLogin")
	public String successLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("", "");
		return "";
	}

}
