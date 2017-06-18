package com.core.filter;

import java.beans.PropertyEditorSupport;

import com.model.UserEntity;

/**
 * 自定义的属性编辑器
 * 
 * @author zhaotf 2017年6月17日 下午3:54:13
 *
 */
public class CustomDeptEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		super.setAsText(text);
		if (text.indexOf(",") > 0) {
			UserEntity dept = new UserEntity();
			String[] arr = text.split(",");
			dept.setId(Long.valueOf(arr[0]));
			dept.setUserName(arr[1]);
			setValue(dept);
		} else {
			throw new IllegalArgumentException("dept param is error");
		}
	}

}
