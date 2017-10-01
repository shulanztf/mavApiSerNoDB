package com.hhcf.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.hhcf.backend.model.UserEntity;

/**
 * 
 * @ClassName: StudentTest
 * @Description:java validation 后台参数验证
 * @see http://www.cnblogs.com/xiaogangfan/p/5987659.html
 * @author: zhaotf
 * @date: 2017年10月1日 下午8:00:25
 */
public class ValidUtils {
	private static Logger logger = Logger.getLogger(ValidUtils.class);

	public static void main(String[] args) {
		UserEntity user = new UserEntity();
		user.setUserName("aa");
		user.setPassword("aa");
		user.setAge(8888);
		List<String> validate = validate(user);
		System.out.println("e:" + JSONArray.toJSONString(validate));
	}

	private static ValidatorFactory factory = Validation
			.buildDefaultValidatorFactory();

	public static <T> List<String> validate(T t) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> cvs = validator.validate(t);
		logger.info("错误原始信息:" + cvs.toString());
		logger.info("错误原始信息AA:" + JSONUtils.valueToString(cvs));

		List<String> messageList = new ArrayList<String>();
		for (ConstraintViolation<T> cv : cvs) {
			logger.info("错误原始信息,分组:" + JSONUtils.valueToString(cv));
			
			messageList.add(cv.getMessage());
		}
		return messageList;
	}

}
