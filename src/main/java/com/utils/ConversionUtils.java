package com.utils;

import java.io.Serializable;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.hhcf.backend.model.TestModel;

/**
 * spring 数据转换工具类
 * 
 * @author zhaotf 2017年6月17日 下午3:27:37
 *
 */
public class ConversionUtils<K extends Comparable<T>, V> {


	public static void main(String[] args) {
		String str1 = "ac";
		ConversionUtils cu = new ConversionUtils(str1, "ddx");
		cu.add("", "");
		
		 TestModel tm = new TestModel();
		 ConversionUtils cu1 = new ConversionUtils((Comparable) tm,"");

		// Collator co = Collator.getInstance();
		// System.out.println("aa:"+co.compare("abce", "33"));
		// System.out.println("aa:"+co.compare("abce", "accs"));
		// Date da1 = new Date();
		// Date da2 = new Date();
		// System.out.println("aa:"+co.compare(da1, da2));
	}

	private K key;
	private V val;

	public ConversionUtils(K key, V val) {
		this.key = key;
		this.val = val;
	}
	
	public void add(K key,V val){
		
	}

	// public class Comp1<? extends Serializable>{
	//
	// }

}
