package com.study.spring.javaconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @ClassName: AppConfig
 * @Description: TODO
 * @see http://www.cnblogs.com/rollenholt/archive/2012/12/27/2835087.html
 * @author zhaotf
 * @date 2017年9月18日 下午9:28:15
 */
@Configuration
@Import({ CustomerConfig.class, SchedulerConfig.class })
public class AppConfig {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				AppConfig.class);
		CustomerBo customer = (CustomerBo) context.getBean("customer");
		customer.printMsg("Hello 中文1");
		SchedulerBo scheduler = (SchedulerBo) context.getBean("scheduler");
		SchedulerBo scheduler2 = (SchedulerBo) context.getBean("scheduler");
		scheduler.printMsg("Hello 汉字2");
		System.out.println("aaaaaaaaaa:" + scheduler.hashCode());
		System.out.println("aaaaaaaaaa:" + scheduler2.hashCode());
		System.out.println("aaaaaaaaaa:" + (scheduler == scheduler2));
	}
	
}
