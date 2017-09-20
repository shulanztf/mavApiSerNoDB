package com.study.system.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.testng.log4testng.Logger;

/**
 * 
 * @Title: ZtfInterceptor
 * @Description:拦截器
 * @Author: zhaotf
 * @Since:2017年9月20日 下午3:07:44
 * @Version:1.0
 */
@Component
@Aspect
public class ZtfInterceptor {
	private static Logger logger = Logger.getLogger(ZtfInterceptor.class);

	/**
	 * 导出总量控制
	 * 
	 * @throws Exception
	 */
	@AfterReturning(pointcut = "execution(* com.zxb.backend.controller.*.*(..))", returning = "rslt")
	public void exportNumCheck(JoinPoint joinPoint, Object rslt) throws Exception {
		logger.info("拦截器:" + rslt);
	}

}
