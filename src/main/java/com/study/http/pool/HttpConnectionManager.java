package com.study.http.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @Title: HttpConnectionManager
 * @Description:线程安全 待解决
 * @see http://www.cnblogs.com/likaitai/p/5431246.html
 * @Author: zhaotf
 * @Since:2017年10月24日 下午3:08:03
 * @Version:1.0
 */
public class HttpConnectionManager {

	public static void main(String[] args) {
		HttpConnectionManager hcm = new HttpConnectionManager();
		String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/postNioHttpAsync.do";
		Map<String, String> params = new HashMap<String, String>();

		params.put("city", "大湖");
		params.put("code", "datc");
		String rslt = hcm.postApply(url, params);
		System.out.println("结果:" + rslt);
	}

	PoolingHttpClientConnectionManager clientPool = null;// 连接池

	/**
	 * post请求
	 * 
	 */
	public String postApply(String url, Map<String, String> args) {
		HttpConnectionManager hcm = new HttpConnectionManager();
		CloseableHttpClient httpClient = hcm.getHttpClient();
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();// 参数用
		if (args != null) {
			for (Entry<String, String> entry : args.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}

		CloseableHttpResponse response = null;
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));// 设置参数，及编码
			response = httpClient.execute(httpRequest);
			InputStream in = response.getEntity().getContent();
			String rslt = IOUtils.toString(in);// 解析返回值
			// 作用就是将用完的连接释放，下次请求可以复用，这里特别注意的是，如果不使用in.close();而仅仅使用response.close();
			// 结果就是连接会被关闭，并且不能被复用，这样就失去了采用连接池的意义。
			in.close();
			System.out.println(rslt);
			return rslt;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();// response必须关闭，避免占用连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public HttpConnectionManager() {
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory()).build();
		clientPool = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		clientPool.setMaxTotal(200);
		clientPool.setDefaultMaxPerRoute(20);
	}

	/**
	 * 实例化连接池，加载spring容器时进行
	 * 
	 */
	@PostConstruct
	public void instancePool() {
		try {
			LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					SSLContext.getDefault());
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("https", sslsf)
					.register("http", new PlainConnectionSocketFactory())
					.build();
			clientPool = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			clientPool.setMaxTotal(200);
			clientPool.setDefaultMaxPerRoute(20);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(clientPool).build();
		// 如果不采用连接池就是这种方式获取连接
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

}
