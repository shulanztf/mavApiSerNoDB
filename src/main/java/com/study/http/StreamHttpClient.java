package com.study.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.study.http.pool.HttpConnectionManagerAsync;

/**
 * 
 * @ClassName: StreamHttpServer
 * @Description:
 * @see http://blog.csdn.net/langzi7758521/article/details/51557179
 * @author: zhaotf
 * @date: 2017年10月28日 下午3:10:41
 */
public class StreamHttpClient {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/testPostHttpStream.do";
		String params = "<boot>http流请求测试</boot>";
		String rslt = postStream(url, params);
		System.out.println("返回结果:" + rslt);
	}

	/**
	 * POST
	 * 
	 * @param url
	 * @param params
	 * @return String
	 * @throws
	 */
	public static String postStream(String url, String params) {
		InputStream is = null;
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpConnectionManagerAsync
					.getHttpClient();
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(params, "UTF-8"));
			response = httpclient.execute(post);
			is = response.getEntity().getContent();
			String rslt = IOUtils.toString(is);
			return rslt;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
