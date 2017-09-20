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
 * @Title: HhcfFilter
 * @Description:
 * @Author: zhaotf
 * @Since:2017年9月20日 下午3:13:02
 * @Version:1.0
 */
public class HhcfFilter implements Filter {
	private static Logger logger = Logger.getLogger(ZtfFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("过滤器:" + filterConfig);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 业务逻辑代码
		// request.setCharacterEncoding("UTF-8");
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB");
		// 继续转发请求(最后必须要写这句话)
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("CCCCCCCCCCCCCCCCCCCCCCC");
		logger.info("过滤器:脚印");
	}

}
