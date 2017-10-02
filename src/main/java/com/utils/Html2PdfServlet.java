package com.utils;

import org.springframework.util.NumberUtils;
import org.xhtmlrenderer.render.Box;

public class Html2PdfServlet {

	public static void main(String[] args) {

		Object obj1 = "";

		Class<? extends Number> c1 = Integer.valueOf(33).getClass();
		Class<String> c2 = String.class;
		Class<? extends Number> c3 = (Class<? extends Number>) obj1.getClass();
		Class<Number> c5 = (Class<Number>) obj1.getClass();

		System.out.println("aaa:"+NumberUtils.parseNumber("33", Integer.valueOf(33).getClass()));
		
//		parseNumber(Integer.valueOf(33).getClass());
		// getData(c1);
		// getUpperData(c1);
//		getData(c3);
//		getUpperData(c1);
//		getData(c5);
//		getUpperData(c5);
		// getData(c2);
		// getUpperData(c2);
	}
	public static <T extends Number> T parseNumber(Class<T> data) {
		System.out.println("aaa " + data.toString());
		return null;
	}
	
	public static void getData(Class<?> data) {
		System.out.println("aa:" + data.toString());
	}

	public static void getUpperData(Class<? extends Number> data) {
		System.out.println("aa:" + data.toString());
	}
	

}
