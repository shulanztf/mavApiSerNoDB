package com.study.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: StreamHttpServer
 * @Description:
 * @see http://blog.csdn.net/langzi7758521/article/details/51557179
 * @author: zhaotf
 * @date: 2017年10月28日 下午3:40:40
 */
@SuppressWarnings("serial")
public class StreamHttpServer extends HttpServlet {
	static Logger logger = Logger.getLogger(StreamHttpServer.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream is = null;
		PrintWriter pw = null;
		try {
			is = req.getInputStream();
			String apply = IOUtils.toString(is);
			logger.info("http服务端流处理:" + apply);
			pw = resp.getWriter();
			pw.write("已处理，" + System.currentTimeMillis() + "," + apply);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (pw != null) {
				pw.close();
			}
		}

	}

}
