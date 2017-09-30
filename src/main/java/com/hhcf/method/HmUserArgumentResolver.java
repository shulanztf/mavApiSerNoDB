package com.hhcf.method;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hhcf.annotation.HmForm;
import com.hhcf.annotation.ZxbForm;
import com.hhcf.backend.model.HmUserEntity;

/**
 * 
 * @Title: HmUserArgumentResolver
 * @Description:springMVC自定义参数解析器
 * @see http://blog.csdn.net/hongxingxiaonan/article/details/50084179
 * @Author: zhaotf
 * @Since:2017年9月30日 下午2:45:33
 * @Version:1.0
 */
public class HmUserArgumentResolver implements HandlerMethodArgumentResolver {
	private static Logger logger = Logger.getLogger(HmUserArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(HmForm.class)) {
			// logger.info("eeeee:" +
			// parameter.getMethodAnnotation(HmForm.class).toString());
			logger.info("eeeee:" + parameter.getParameterAnnotation(HmForm.class).toString());
			logger.info("eeeee:" + parameter.getParameterAnnotation(HmForm.class).value());
			logger.info("eeeee:" + parameter.getParameterAnnotation(HmForm.class).name());
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String objName = parameter.getParameterAnnotation(HmForm.class).value() + ".";// 获取注解里的逻辑名
		Object obj = parameter.getParameterType().newInstance();// 实例化对象

		StringBuffer tmp = new StringBuffer();
		String[] val;
		Field[] frr = parameter.getParameterType().getDeclaredFields();
		for (Iterator<String> itr = webRequest.getParameterNames(); itr.hasNext();) {
			tmp.delete(0, tmp.length());
			tmp.append(itr.next());
			if (tmp.indexOf(objName) < 0) {
				continue;
			}
			for (int i = 0; i < frr.length; i++) {
				frr[i].setAccessible(true);
				String filed = objName + frr[i].getName();
				if (tmp.toString().equals(filed)) {
					val = webRequest.getParameterValues(tmp.toString());
					frr[i].set(obj, val[0]);
				}
			}
		}
		return obj;
	}

}
