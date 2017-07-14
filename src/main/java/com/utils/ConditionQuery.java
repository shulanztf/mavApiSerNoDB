package com.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

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
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("aaa", "1111");
		m1.put("bbb", 2222);
		m1.put("ccc", new Date());
		m1.put("ddd", new Date());
		m1.put("eee", new Date());
		m1.put("fff", new Date());
		m1.put("ggg", new Date());
		m1.put("hhh", new Date());
		m1.put("jjj", new Date());
		m1.put("kkk", new Date());
		m1.put("lll", new Date());
		m1.put("sss", new Date());
		m1.put("qqq", new Date());
		m1.put("www", new Date());
		m1.put("rrr", new Date());
		m1.put("ttt", new Date());
		for (String key : m1.keySet()) {
			System.out.println("一:" + key + ":" + m1.get(key));
		}
		System.out.println("");
		m1.put("1", new Date());
		m1.put("2", new Date());
		m1.put("3", new Date());
		for (String key : m1.keySet()) {
			System.out.println("二:" + key + ":" + m1.get(key));
		}
		System.out.println("");

		// Map<String, Object> ml1 = new LinkedHashMap<String, Object>();
		// ml1.put("b", new Date());
		// ml1.put("a", 1111);
		// ml1.put("c", 2222);
		// System.out.println(ml1.keySet().toString());
		// for (String key : ml1.keySet()) {
		// System.out.println(key + ":" + ml1.get(key));
		// }
		//
		// Map<String, Object> m2 = new TreeMap<String, Object>();
		// m2.put("bb", new Date());
		// m2.put("aa", 1111);
		// m2.put("cc", "有eww eeee");
		// System.out.println(m2.keySet().toString());
		// for (String key : m2.keySet()) {
		// System.out.println(key + ":" + m2.get(key));
		// }

	}

}
