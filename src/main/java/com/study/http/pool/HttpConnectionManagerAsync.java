package com.study.http.pool;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
 * @ClassName: HttpConnectionManagerAsync
 * @Description:连接池，线程安全版
 * @see http://www.cnblogs.com/likaitai/p/5431246.html
 * @author: zhaotf
 * @date: 2017年10月24日 下午8:00:32
 */
public class HttpConnectionManagerAsync {

	public static void main(String[] args) {
		final HttpConnectionManagerAsync hcm = new HttpConnectionManagerAsync();
		ExecutorService es = Executors.newCachedThreadPool();
		final String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/postNioHttpAsync.do";

		for (int i = 0; i < 50; i++) {
			es.execute(new Runnable() {

				@Override
				public void run() {
					Map<String, String> params = new HashMap<String, String>();

					params.put("city", Thread.currentThread().getId() + "大湖");
					params.put("code", System.currentTimeMillis() + "datc");
					System.out.println(Thread.currentThread().getId()
							+ ":发送参数:" + params.toString());
					String rslt = hcm.postApply(url, params);
					System.out.println(Thread.currentThread().getId() + ":结果:"
							+ rslt);
				}
			});
		}

		hcm.clientPool.closeExpiredConnections();// 关闭过期的连接
		hcm.clientPool.closeIdleConnections(30, TimeUnit.SECONDS); // 可选的,关闭30秒内不活动的连接
		Scanner sca = new Scanner(System.in);
		while (!"q".equals(sca.nextLine())) {
		}
		es.shutdown();
		System.out.println("测试结束......");

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
			System.out.println(rslt + ":" + httpClient.hashCode());
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

	public HttpConnectionManagerAsync() {
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
		clientPool.setMaxTotal(10);// 将最大连接数增加
		clientPool.setDefaultMaxPerRoute(10);// 将每个路由基础的连接增加
	}

	public CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(clientPool).build();
		// 如果不采用连接池就是这种方式获取连接
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

}
