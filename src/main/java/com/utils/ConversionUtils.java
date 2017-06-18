package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.model.TestModel;

/**
 * spring 数据转换工具类
 * 
 * @author zhaotf 2017年6月17日 下午3:27:37
 *
 */
public class ConversionUtils {

	public static void main(String[] args) {
		TestModel tm = new TestModel();
		BeanWrapper bw = new BeanWrapperImpl(tm);
		bw.setPropertyValue("good", "on");
		// bw.setPropertyValue("good", "1");
		// bw.setPropertyValue("good", "true");
		// bw.setPropertyValue("good", "yes");
		System.out.println(tm.isGood());

		// TestModel tm = new TestModel();
		// BeanWrapper bw = new BeanWrapperImpl(tm);
		bw.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
		bw.setPropertyValue("birth", "1990-01-01");
		System.out.println(tm.getBirth().toString());

		// TestModel tm = new TestModel();
		// BeanWrapperImpl bw1 = new BeanWrapperImpl(false);
		// bw1.setWrappedInstance(tm);
		// bw1.setPropertyValue("good", "1");
		// System.out.println(tm.isGood());

	}

}
