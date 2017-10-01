package com.hhcf.validate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.hhcf.backend.model.UserEntity;

/**
 * 
 * @ClassName: ValidatorUtil
 * @Description:校验工具类
 * @author: zhaotf
 * @date: 2017年10月1日 下午4:09:29
 */
public class ValidatorUtil {
	private static Logger logger = Logger.getLogger(ValidatorUtil.class);
	private static Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	public static <T> Map<String, StringBuffer> validate(T obj) {
		Set<ConstraintViolation<T>> set = validator
				.validate(obj, Default.class);
		logger.info("错误原始信息:" + JSONObject.toJSONString(set));
		Map<String, StringBuffer> errorMap = new HashMap<String, StringBuffer>();
		String property = null;
		for (ConstraintViolation<T> cv : set) {
			// 这里循环获取错误信息，可以自定义格式
			property = cv.getPropertyPath().toString();
			logger.info("aaa:" + property);
			if (errorMap.get(property) != null) {
				errorMap.get(property).append("," + cv.getMessage());
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append(cv.getMessage());
				errorMap.put(property, sb);
			}
			logger.info("ttt:" + errorMap.toString());
		}
		return errorMap;
	}

	public static void main(String[] args) {
		UserEntity user = new UserEntity();
		user.setUserName("");
		user.setAge(99);
		user.setPassword("ccccccccccccccccccccccccccccc");
		print(ValidatorUtil.validate(user));
	}

	private static void print(Map<String, StringBuffer> errorMap) {
		logger.info("校验共通类:" + JSONObject.toJSONString(errorMap));
	}

}
