package com.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 
 * @Title: MySecurityMetadataServiceImpl
 * @Description:
 * @Author:Administrator
 * @Since:2016年9月5日 下午1:38:23
 * @Version:1.1.0
 */
public class MySecurityMetadataServiceImpl implements FilterInvocationSecurityMetadataSource {

//	@Resource
//	private PermissionDao permDao;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//		String url = ((FilterInvocation) object).getRequestUrl();
//		int firstQuestionMarkIndex = url.indexOf("?");
//		if (firstQuestionMarkIndex != -1) {
//			url = url.substring(0, firstQuestionMarkIndex);
//		}
//		if (firstQuestionMarkIndex != -1) {
//			url = url.substring(0, firstQuestionMarkIndex);
//		}
//		System.out.println("url:" + url);
		List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
//		ConfigAttribute attribute = new SecurityConfig("ROLE_BASE");
//		result.add(attribute);
//		try {
//			List<Permission> permList = permDao.getPermissionByUrl(url);
//			for (Permission permission : permList) {
//				ConfigAttribute conf = new SecurityConfig(permission.getPermName());
//				result.add(conf);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
