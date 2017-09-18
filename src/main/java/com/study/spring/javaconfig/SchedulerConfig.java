package com.study.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @ClassName: SchedulerConfig
 * @Description: TODO
 * @see http://www.cnblogs.com/rollenholt/archive/2012/12/27/2835087.html
 * @author zhaotf
 * @date 2017年9月18日 下午9:27:42
 */
@Configuration
public class SchedulerConfig {
	@Bean(name = "scheduler")
	public SchedulerBo suchedulerBo() {
		return new SchedulerBo();
	}
}
