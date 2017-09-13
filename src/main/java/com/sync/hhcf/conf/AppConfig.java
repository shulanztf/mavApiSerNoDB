package com.sync.hhcf.conf;

import org.springframework.context.annotation.Configuration;

import com.sync.hhcf.zxb.service.AsyncTaskService;

/**
 * 
 * @Title: AppConfig
 * @Description:Spring java 注解支持，替代xml
 * @Author: zhaotf
 * @Since:2017年9月12日 上午8:13:30
 * @Version:1.0
 */
@Configuration
public class AppConfig {

	public AsyncTaskService getAsyncTaskService() {
		return new AsyncTaskService();
	}
}
