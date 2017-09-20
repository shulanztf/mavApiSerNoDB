package com.hhcf.backend.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhcf.backend.model.TestModel;
import com.hhcf.backend.model.UserEntity;

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
	private static final Logger logger = Logger
			.getLogger(BootStarpController.class);

	/**
	 * @category nginx反向代理
	 * @see http://localhost:8080/mavApiSerNoDB/bootStarpController/nginxReversedProxy.do
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/nginxReversedProxy")
	public String nginxReversedProxy(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			logger.info("nginx反向代理");
			model.addAttribute("bs_test", "服务器返回数据");
		} catch (Exception e) {
			logger.error(e);
		}
		return "views/nginxTest1";
	}

	/**
	 * @see http://localhost:8080/mavApiSerNoDB/bootStarpController/toLogin.do
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAttribute("bs_test", "学习");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "bootstarp/login_bs";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/bootStarpController/validateLogin.do
	 * 
	 * @return
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "/validateLogin")
	public Object validateLogin(HttpServletRequest request,
			HttpServletResponse response, Model model, String loginObj,
			String accountNo, String pwd) {
		JSONObject result = new JSONObject();
		try {
			logger.info("nginx反向代理11");
			result.put("total", 11);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return result;
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/bootStarpController/validateLogin1.do
	 * 
	 * @param request
	 * @param loginObj
	 * @param accountNo
	 * @param pwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateLogin1")
	public Object validateLogin1(HttpServletRequest request, String loginObj,
			String accountNo, String pwd) {
		UserEntity user = new UserEntity();
		user.setId(11L);
		user.setUserName("abc有人");
		return user;
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/bootStarpController/validateLogin2.do
	 * 
	 * @param request
	 * @param loginObj
	 * @param accountNo
	 * @param pwd
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/validateLogin2")
	public Object validateLogin2(HttpServletRequest request,
			HttpServletResponse response, String loginObj, String accountNo,
			String pwd) throws IOException {
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		ClassLoader loader = this.getClass().getClassLoader();
//		while (loader != null) {
//			out.write(loader.getClass().getName() + "<br/>");
//			loader = loader.getParent();
//		}
//		out.write(String.valueOf(loader));
//		out.flush();
//		out.close();
		TestModel tm = new TestModel();
		tm.setName("model中不仍");
		tm.setBirth(new Date());
		return tm;
	}

	/**
	 * 登录验证通过后，跳转至主页
	 * 
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "successLogin")
	public String successLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("", "");
		return "";
	}

}
