package com.study.system.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.testng.log4testng.Logger;

import com.hhcf.backend.controller.GeneralController;

/**
 * 
 * @Title: ZtfInitListener
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月20日 下午2:55:43
 * @Version:1.0
 */
public class ZtfInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = Logger.getLogger(ZtfInitListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("监听器-脚印---------------");
		if (event.getApplicationContext().getParent() == null) {

			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			ApplicationContext context = event.getApplicationContext();
			GeneralController general = (GeneralController) context.getBean("generalController");
			logger.info("监听器:" + general);
		} else {
			logger.info("监听器-脚印aaa---------------");
		}
	}

}
