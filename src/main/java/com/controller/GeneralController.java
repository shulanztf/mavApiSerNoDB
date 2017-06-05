package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.model.HmAppProductMgEntity;
import com.service.GeneralService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title: GeneralController
 * @Description:
 * @author zhaotf
 * @date 2016��8��22�� ����11:14:20
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/generalController")
public class GeneralController {
	private static final Logger logger = Logger.getLogger(GeneralController.class);

	@Resource
	private GeneralService generalService;

	/**
	 * http://localhost:8080/mavApiSerNoDB/generalController/home.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("liming", "好了");
			// HmAppProductMgEntity entity =
			// this.generalService.getHmAppProductMg(2L);
			// System.out.println(entity.getId());
			logger.info("好了");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "index";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/generalController/bootStarpTest.do
	 * 
	 * @Description:
	 */
	@RequestMapping(value = "bootStarpTest")
	public String bootStarpTest(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("bsTest", "bs学习");
		return "bootstarp/head";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/generalController/list.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("liming", "好了");
			// HmAppProductMgEntity entity =
			// this.generalService.getHmAppProductMg(2L);
			// System.out.println(entity.getId());
			System.out.println("好了");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "com11/datagrid_bufferview";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/generalController/findList.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public Object findList(HttpServletRequest request, HttpServletResponse response, Model model, Integer page,
			Integer rows) {
		JSONObject result = new JSONObject();
		try {
			List<HmAppProductMgEntity> list = new ArrayList<HmAppProductMgEntity>();
			list.add(generalService.getHmAppProductMg(3l));
			model.addAttribute("liming", "拿到数据");

			JSONObject params = new JSONObject();
			params.put("pageSize", rows);
			params.put("pageIndex", (page - 1) * rows);
			result.put("rows", list);
			result.put("total", 11);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/generalController/getAllParam.do
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param paramName
	 * @param createTime
	 * @return
	 * @throws IOException
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "getAllParam")
	public Object getAllParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "10") Integer rows, // 页数大小
			@RequestParam(required = false, defaultValue = "") String paramName,
			@RequestParam(required = false, defaultValue = "") String createTime) throws IOException {
		JSONObject params = new JSONObject();
		params.put("pageSize", rows);
		params.put("pageIndex", (page - 1) * rows);
		List<HmAppProductMgEntity> list = new ArrayList<HmAppProductMgEntity>();
		for (int i = 0; i < 20; i++) {
			HmAppProductMgEntity entity = new HmAppProductMgEntity();
			entity.setId(i + 1l);
			entity.setName("标的" + i);
			entity.setTotalmoney(new BigDecimal(i));
			entity.setInserttime(new Date());
			list.add(entity);
		}

		JSONObject result = new JSONObject();
		result.put("rows", list);
		result.put("total", 11);
		return JSON.toJSONString(result);// org.springframework.http.converter.StringHttpMessageConverter
		// return result; //
		// com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
	}
}
