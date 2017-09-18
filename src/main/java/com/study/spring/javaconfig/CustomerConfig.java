package com.study.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @ClassName: CustomerConfig
 * @Description: TODO
 * @see http://www.cnblogs.com/rollenholt/archive/2012/12/27/2835087.html
 * @author zhaotf
 * @date 2017年9月18日 下午9:26:45
 */
@Configuration
public class CustomerConfig {
	@Bean(name = "customer")
	public CustomerBo customerBo() {
		return new CustomerBo();
	}
}
