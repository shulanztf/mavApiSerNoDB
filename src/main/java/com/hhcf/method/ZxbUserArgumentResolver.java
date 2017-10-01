package com.hhcf.method;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hhcf.annotation.ZxbForm;

/**
 * 
 * @Title: ZxbUserArgumentResolver
 * @Description:
 * @see http://blog.csdn.net/truong/article/details/30971317
 * @Author: zhaotf
 * @Since:2017年9月30日 下午3:18:17
 * @Version:1.0
 */
public class ZxbUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(ZxbForm.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		String objName = parameter.getParameterAnnotation(ZxbForm.class)
				.value() + ".";// 获取注解里的逻辑名
		Object obj = parameter.getParameterType().newInstance();// 实例化对象

		StringBuffer tmp = new StringBuffer();
		String[] val;
		Field fie = null;
		Field[] frr = parameter.getParameterType().getDeclaredFields();
		for (Iterator<String> itr = webRequest.getParameterNames(); itr
				.hasNext();) {
			tmp.delete(0, tmp.length());
			tmp.append(itr.next());
			if (tmp.indexOf(objName) < 0) {
				continue;
			}
			for (int i = 0; i < frr.length; i++) {
				frr[i].setAccessible(true);
				String filed = objName + frr[i].getName();
				if (tmp.toString().equals(filed)) {
					// TODO 缺少类型转换 java.lang.IllegalArgumentException: Can not
					// set java.lang.Integer field
					// com.hhcf.backend.model.HmUserEntity.age to
					// java.lang.String
					val = webRequest.getParameterValues(tmp.toString());
					fie = frr[i];
					fie.set(obj, val[0]);
				}
			}
		}
		return obj;
	}

}
