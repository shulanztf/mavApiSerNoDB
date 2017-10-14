package com.study.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @ClassName: WhatThis
 * @Description:
 * @see http://www.cnblogs.com/aoeiuv/p/5911692.html
 * @author: zhaotf
 * @date: 2017年10月13日 下午9:10:57
 */
public class WhatThis {

	public void method1() {
		List<String> list = (List<String>) Arrays.asList(new String[] { "ab",
				"c", "ee" });
		List<String> list2 = list.stream().map(str -> {
			System.out.println("clase:" + this.getClass().getName());
			return "abc-" + str + ":" + Thread.currentThread().getId();
		}).collect(Collectors.toList());
		list2.forEach(System.out::println);
	}

	public static void main(String[] args) {
		WhatThis wt = new WhatThis();
		wt.method1();
	}

}
