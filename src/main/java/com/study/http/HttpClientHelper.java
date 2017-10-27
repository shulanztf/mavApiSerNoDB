package com.study.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.study.http.pool.HttpConnectionManagerAsync;

/**
 * 
 * @Title: HttpClientHelper
 * @Description: 接口工具类
 * @Author: zhaotf
 * @Since:2017年6月9日 上午10:24:39
 * @Version:1.0
 */
public class HttpClientHelper {
	private static final Logger logger = Logger.getLogger(HttpClientHelper.class);

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		final String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/postNioHttpAsync.do";

		for (int i = 0; i < 50; i++) {
			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Map<String, String> params = new HashMap<String, String>();

						params.put("city", Thread.currentThread().getId() + "大湖");
						params.put("code", System.currentTimeMillis() + "datc");
						System.out.println(Thread.currentThread().getId() + ":发送参数:" + params.toString());
						String rslt;
						rslt = sendHttp(url, params);
						System.out.println(Thread.currentThread().getId() + ":结果:" + rslt);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		Scanner sca = new Scanner(System.in);
		while (!"q".equals(sca.nextLine())) {
		}
		sca.close();
		es.shutdown();
	}

	/**
	 * 请求接口 入口
	 * 
	 * @param url
	 * @param parameters
	 *            参数
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String sendHttp(String url, Map<String, String> parameters) throws Exception {
		String outStr = StringUtils.EMPTY;
		try {
			String charSet = "UTF-8";
			int timeOut = 20000;// 自行配置超时毫秒数设置
			outStr = postHttp(url, charSet, parameters, timeOut);
			if (StringUtils.isBlank(outStr)) {
				throw new Exception("接口调用失败");
			}
		} catch (Exception e) {
			logger.error("接口调用失败", e);
			throw new Exception("接口调用失败", e);
		}
		return outStr;
	}

	/**
	 * 请求发送接口
	 * 
	 * @param urlStr
	 * @param charSet
	 * @param parameters
	 * @param timeOut
	 * @return String
	 */
	public static String postHttp(String urlStr, String charSet, Map<String, String> parameters, int timeOut)
			throws Exception {
		String responseString = "";
		PostMethod xmlpost;
		int statusCode = 0;
		HttpClient httpclient = new HttpClient();
		httpclient.setConnectionTimeout(timeOut);
		xmlpost = new PostMethod(urlStr);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
		try {
			// 组合请求参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : parameters.entrySet()) {
				try {
					NameValuePair nvp = new NameValuePair(entry.getKey(), entry.getValue());
					list.add(nvp);
				} catch (Exception e) {
					logger.error("存管接口调用", e);
					throw e;
				}

			}
			NameValuePair[] nvps = new NameValuePair[list.size()];
			xmlpost.setRequestBody(list.toArray(nvps));
			statusCode = httpclient.executeMethod(xmlpost);
			responseString = xmlpost.getResponseBodyAsString();
			if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
				logger.error("存管接口调用，失败返回码[" + statusCode + "]");
				throw new Exception("存管接口调用，失败码[ " + statusCode + " ]");
			}
		} catch (IOException e) {
			logger.error("存管接口调用", e);
			throw e;
		}
		return responseString;
	}

}
