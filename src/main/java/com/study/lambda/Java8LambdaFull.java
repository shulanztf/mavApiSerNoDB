package com.study.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.testng.collections.Lists;

/**
 * 
 * @ClassName: Java8LambdaFull
 * @Description:JAVA8lambda表达式
 * @see http://www.cnblogs.com/aoeiuv/p/5911692.html
 * @author: zhaotf
 * @date: 2017年10月13日 下午7:32:59
 */
public class Java8LambdaFull {

	public static void main(String[] args) {
		Java8LambdaFull lambda = new Java8LambdaFull();
		lambda.method5();
	}

	/**
	 * 3. map:
	 * 对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。这个方法有三个对于原始类型的变种方法
	 * ，分别是：mapToInt，mapToLong和mapToDouble。这三个方法也比较好理解，
	 * 比如mapToInt就是把原始Stream转换成一个新的Stream
	 * ，这个新生成的Stream中的元素都是int类型。之所以会有这样三个变种方法，可以免除自动装箱/拆箱的额外消耗；
	 */
	public void method6() {

	}

	/**
	 * 3.2转换Stream
	 */
	public void method5() {
		// 1. distinct:
		// 对于Stream中包含的元素进行去重操作（去重逻辑依赖元素的equals方法），新生成的Stream中没有重复的元素；
		List<String> list = Arrays.asList(new String[] { "aa", "AA", "553",
				null, "dae", "cc", null, "" });
		List<String> list1 = list.stream().distinct()
				.collect(Collectors.toList());
		System.out.println("ccc:" + list1.toString());

		// 2. filter: 对于Stream中包含的元素使用给定的过滤函数进行过滤操作，新生成的Stream只包含符合条件的元素；
		Stream<String> filter = list.stream().filter(
				(str1) -> StringUtils.isNotBlank(str1));
		System.out.println(filter.collect(Collectors.toList()).toString());
		Stream<String> filter1 = list.stream().filter(
				(str1) -> StringUtils.isNotBlank(str1)
						&& StringUtils.length(str1) > 2);
		System.out.println(filter1.collect(Collectors.toList()).toString());
	}

	/**
	 * 3.Stream语法
	 */
	public void method4() {
		List<Integer> list = Lists.newArrayList(2, 3, 3, null, 4, null);
		long l5 = list.stream().filter(nu -> nu != null).count();
		List<Integer> it2 = list.stream().filter(nu -> nu != null)
				.collect(Collectors.toList());
		System.out.println("ttt:" + l5);
		System.out.println("ttt:" + it2.toString());
	}

	/**
	 * 1.2lambda表达式可使用的变量
	 */
	public void method3() {
		// 将为列表中的字符串添加前缀字符串
		String waibu = "pp:";
		List<String> list = Arrays.asList(new String[] { "ac", "es", "aaa" });
		List<String> list1 = list.stream().map((str) -> {
			Long long1 = System.currentTimeMillis();
			return waibu + str + "-" + long1;
		}).collect(Collectors.toList());
		System.out.println("eee:" + list1.toString());
		// 变量 waibu ：外部变量
		// 变量 str ：传递变量
		// 变量 long1 ：内部自定义变量
	}

	/**
	 * 1.1.4方法引用写法
	 * 
	 * @see http://www.cnblogs.com/aoeiuv/p/5911692.html
	 */
	public void method2() {
		// 1.1.4方法引用写法
		// Class or instance :: method
		// 例如：将列表中的字符串转换为全小写
		List<String> list = Arrays
				.asList(new String[] { "EdESD", "CCaE", "aAA" });
		List<String> list1 = list.stream().map(String::toLowerCase)
				.collect(Collectors.toList());
		System.out.println("aaa:" + list1.toString());
	}

	public Object method1() {
		// 零个
		System.out.println("java8-aaaaaaaa");
		// 匿名类，无参数写法
		// 创建线程
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("无参匿名类，旧有写法");
			}
		});
		th.start();
		Thread th1 = new Thread(() -> System.out.println("无参匿名类，JAVA8写法"));
		th1.start();

		// 匿名类，有参数写法
		List<String> list = (List<String>) Arrays.asList(new String[] { "aa",
				"bb", "cc" });
		List<String> list1 = new ArrayList<String>();
		for (String key : list) {
			list1.add(key);
		}
		System.out.println("ttttt:" + list1.toString());

		List<String> list2 = list.stream().map(key -> {
			return key + "后台";
		}).collect(Collectors.toList());
		System.out.println("cccccc:" + list2.toString());

		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});
		System.out.println(list.toString());
		Collections.sort(list, (str1, str2) -> str1.compareTo(str2));
		System.out.println(list.toString());
		return null;
	}

}
