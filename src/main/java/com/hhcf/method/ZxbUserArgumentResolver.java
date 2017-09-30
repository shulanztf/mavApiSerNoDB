package com.hhcf.method;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hhcf.annotation.ZxbForm;
import com.hhcf.backend.model.ZxbUserEntity;

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
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		List<ZxbUserEntity> users = new ArrayList<ZxbUserEntity>();
		String names = (String) webRequest.getParameter("names");
		String ids = (String) webRequest.getParameter("ids");
		if (null != names && null != ids) {
			String[] nameStrs = names.trim().split(",");
			String[] idStrs = ids.trim().split(",");
			for (int i = 0; i < nameStrs.length; i++) {
				ZxbUserEntity user = new ZxbUserEntity();
				users.add(user);
			}
		}
		return users;
	}

}
