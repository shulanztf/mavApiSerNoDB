package com.hhcf.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
	private static ValidatorFactory factory = Validation
			.buildDefaultValidatorFactory();

	public static void main(String[] args) {
		UserEntity user = new UserEntity();
		System.out.println("tt:" + user.getClass().getName());
		System.out.println("tt:" + user.getClass().getCanonicalName());
		System.out.println("tt:" + user.getClass().getSimpleName());
		
		System.out.println("cc:" + UserEntity.class.getSimpleName());
		
//		System.out.println("tt:" + toLowerCaseFirstOne(user.getClass().getSimpleName()));
		// System.out.println("tt:" + user.getClass().get());

		//
//		 user.setUserName("aa");
//		 user.setPassword("aaaaaaa");
//		 user.setAge(8888);
////		  List<ObjectError> validate = validate(user);
//		 Errors validate = validate(user);
//		 System.out.println("tt:" + user.getClass().getName());
//		 System.out.println("oo:" + validate.getObjectName());
		//
		// // System.out.println("e:" + JSONUtils.valueToString(validate));
		// System.out.println("e:" + validate.toString());
	}

	public static <T> MapBindingResult validate(T t) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> cvs = validator.validate(t);
		logger.info("错误原始信息:" + cvs.toString());
		logger.info("错误原始信息AA:" + JSONUtils.valueToString(cvs));

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectError err = null;
		for (ConstraintViolation<T> cv : cvs) {
			// cv.getMessage();
			// cv.getInvalidValue();
			// cv.getPropertyPath();

			logger.info("错误原始信息,分组:" + JSONUtils.valueToString(cv));
			map.put(t.getClass().getName() + "."
					+ cv.getPropertyPath().toString(), cv);

			// err = new
			// ObjectError(t.getClass().getName()+"."+cv.getPropertyPath().toString(),
			// cv.getMessage());
			// messageList.add(err);
		}
		MapBindingResult errors = new MapBindingResult(map, t.getClass()
				.getName());
		return errors;
	}

	// MapBindingResult

	public static <T> List<ObjectError> validate1(T t) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> cvs = validator.validate(t);
		logger.info("错误原始信息:" + cvs.toString());
		logger.info("错误原始信息AA:" + JSONUtils.valueToString(cvs));

		List<ObjectError> messageList = new ArrayList<ObjectError>();
		ObjectError err = null;
		for (ConstraintViolation<T> cv : cvs) {
			// cv.getMessage();
			// cv.getInvalidValue();
			// cv.getPropertyPath();

			logger.info("错误原始信息,分组:" + JSONUtils.valueToString(cv));

			err = new ObjectError(t.getClass().getName() + "."
					+ cv.getPropertyPath().toString(), cv.getMessage());
			messageList.add(err);
		}
		return messageList;
	}

	/**
	 * 首字母转小写
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {
			return s;

		}
		return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
				.append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		}
		return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0)))
				.append(s.substring(1)).toString();
	}

}
