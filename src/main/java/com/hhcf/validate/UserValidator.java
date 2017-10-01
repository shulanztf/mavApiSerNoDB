package com.hhcf.validate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hhcf.backend.model.UserEntity;

/**
 * 
 * @ClassName: UserValidator
 * @Description:
 * @see http://elim.iteye.com/blog/1812584
 * @author: zhaotf
 * @date: 2017年10月1日 下午3:25:29
 */
public class UserValidator implements Validator {
	private static Logger logger = Logger.getLogger(UserValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("abcde:" + clazz);
		return UserEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("aaaaaaaa:" + errors);
		UserEntity user = (UserEntity) target;
		if (StringUtils.isBlank(user.getPassword())) {
			logger.info("aaaaaaaa11:" + errors);
			errors.rejectValue("password", null, "Password is empty.");
		}
	}
}
