package com.utils;

import java.util.Scanner;

/**
 * @Title: ConditionQuery
 * @Description:
 * @author zhaotf
 * @date 2016年8月25日 下午1:22:30
 * @version V1.0
 *
 */
public class ConditionQuery {

	public static void main(String[] args) {
//		System.out.println(System.getProperty("sun.boot.class.path"));
		ClassLoader loader = ConditionQuery.class.getClassLoader();    //获得加载ClassLoaderTest.class这个类的类加载器  
		while(loader != null) {  
		    System.out.println(loader);  
		    loader = loader.getParent();    //获得父类加载器的引用  
		}  
		System.out.println(loader);  
	}


}
