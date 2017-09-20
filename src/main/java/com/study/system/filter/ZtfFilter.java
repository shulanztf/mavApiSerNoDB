package com.study.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.testng.log4testng.Logger;

/**
 * 
 * @Title: ZtfFilter
 * @Description:Filter 过滤器
 * @Author: zhaotf
 * @Since:2017年9月20日 下午3:09:59
 * @Version:1.0
 */
public class ZtfFilter implements Filter {
	/** 过滤器里的日志不可用，调用过滤器时，日志组件还未加载 */
	private static Logger logger = Logger.getLogger(ZtfFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("过滤器:" + filterConfig);
		System.out.println("aaaaaaaaaaaaaaa");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 业务逻辑代码
		// request.setCharacterEncoding("UTF-8");
		System.out.println("bbbbbbbbbbbbbbbbb");
		logger.info("过滤器:" + request + "," + response + "," + chain);
		// 继续转发请求(最后必须要写这句话)
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("cccccccccccccccc");
		logger.info("过滤器:脚印");
	}

}
