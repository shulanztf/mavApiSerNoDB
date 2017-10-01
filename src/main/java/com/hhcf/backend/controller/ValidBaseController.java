package com.hhcf.backend.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhcf.annotation.HmForm;
import com.hhcf.backend.model.UserEntity;
import com.hhcf.backend.validate.VaildGroup1;

/**
 * 
 * @ClassName: ValidBaseController
 * @Description:SpringMVC中的数据校验
 * @see http://blog.csdn.net/eson_15/article/details/51725470
 * @author: zhaotf
 * @date: 2017年9月30日 下午9:38:51
 */
@Controller
@RequestMapping("/validBase")
public class ValidBaseController {
	private static Logger logger = Logger.getLogger(ValidBaseController.class);

	/**
	 * @see http://127.0.0.1:8080/mavApiSerNoDB/validBase/validaHiberArgu.do
	 */
	@ResponseBody
	@RequestMapping("/validaHiberArgu")
	public Object validaHiberArgu(ModelAndView mv,
			@HmForm("al") UserEntity user, BindingResult bindingResult)
			throws Exception {
		logger.info("参数校验:" + JSONObject.toJSONString(user));

		if (bindingResult.hasErrors()) {
			List<ObjectError> errList = bindingResult.getAllErrors();
			for (ObjectError err : errList) {
				logger.error("参数校验错误信息:" + err.getDefaultMessage());
			}
		}
		mv.getModel().put("err", bindingResult.getAllErrors());
		return mv.getModel();
	}

	/**
	 * @see http://127.0.0.1:8080/mavApiSerNoDB/validBase/validaHiberParams.do
	 */
	@ResponseBody
	@RequestMapping("/validaHiberParams")
	public Object validaHiberParams(@Valid UserEntity user,
			BindingResult bindingResult) throws Exception {
		logger.info("参数校验:" + JSONObject.toJSONString(user));

		if (bindingResult.hasErrors()) {
			List<ObjectError> errList = bindingResult.getAllErrors();
			for (ObjectError err : errList) {
				logger.error("参数校验错误信息:" + err.getDefaultMessage());
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.getModel().put("err", bindingResult.getAllErrors());
		return mv.getModel();
	}

}
