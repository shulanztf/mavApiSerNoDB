package com.study.nio.httpasync;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title: SimpleHttpAsyncClientDemoLocal
 * @Description:异步HttpAsyncClient连接池
 * @see {@link http://blog.csdn.net/xiaoxian8023/article/details/49949813}
 * @Author: zhaotf
 * @Since:2017年11月2日 上午8:21:16
 * @Version:1.0
 */
public class SimpleHttpAsyncClientDemoLocal {

	public static void main(String[] args) {
		SimpleHttpAsyncClientDemoLocal local = new SimpleHttpAsyncClientDemoLocal();
		String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/postNioHttpAsync.do";
		try {
			local.init();
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", new Date().toString());
			map.put("cityname", "中为");
			map.put("city", "联想");
			doPost(url, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Logger logger = LoggerFactory.getLogger(SimpleHttpAsyncClientDemoLocal.class);
	public final static String CHARSET_UTF8 = StandardCharsets.UTF_8.name();
	private static RequestConfig requestConfig;// 连接配置
	private static PoolingNHttpClientConnectionManager poolManager;// 连接池管理器

	/**
	 * 实例化连接池
	 * 
	 * void
	 * 
	 * @throws KeyManagementException
	 * @throws IOReactorException
	 */
	public void init() throws Exception {
		SSLContext sslContext = SSLContext.getInstance("SSLv3");
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		};
		sslContext.init(null, new TrustManager[] { trustManager }, null);

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<SchemeIOSessionStrategy> registry = RegistryBuilder.<SchemeIOSessionStrategy> create()
				.register(HttpHost.DEFAULT_SCHEME_NAME, NoopIOSessionStrategy.INSTANCE)
				.register("https", new SSLIOSessionStrategy(sslContext)).build();
		// 配置io线程
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
				.setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		poolManager = new PoolingNHttpClientConnectionManager(ioReactor, registry);
		poolManager.setMaxTotal(100);// 设置连接池大小
		poolManager.setDefaultMaxPerRoute(poolManager.getMaxTotal());// 单台服务器最大并发数

		requestConfig = RequestConfig.custom().setConnectTimeout(1000 * 10)// 建立连接的超时时间
				.setSocketTimeout(1000 * 10)// 数据传输处理时间
				.build();
	}

	/**
	 * 
	 * @return CloseableHttpAsyncClient
	 */
	public static CloseableHttpAsyncClient getHttpClient() {
		// 创建自定义的httpclient对象
		CloseableHttpAsyncClient client = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolManager).build();
		client.start();// TODO
		return client;
	}

	/**
	 * 关闭client对象
	 * 
	 * @param client
	 */
	private static void close(CloseableHttpAsyncClient client) {
		try {
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 * @return String
	 */
	public static void doGet(String url) {
		try {
			url = URLDecoder.decode(url, CHARSET_UTF8);
			HttpGet request = new HttpGet(url);
			final CloseableHttpAsyncClient client = getHttpClient();
			client.execute(request, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse result) {
					try {
						HttpEntity entity = result.getEntity();
						String rslt = EntityUtils.toString(entity);
						logger.info("池化处理异步httpclient，get返回结果: {}", rslt);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						close(client); // 关闭连接
					}
				}

				@Override
				public void failed(Exception ex) {
					logger.error("http get请求失败", ex);
					close(client); // 失败时关闭连接
				}

				@Override
				public void cancelled() {
					close(client); // 服务取消时关闭连接
				}
			});
		} catch (Exception e) {
			logger.error("http get请求异常:" + url, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * http POST文本流
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return String
	 */
	public static void doPost(String url, String params, Header[] headers) {
		try {
			HttpPost post = new HttpPost(url);
			post.setHeaders(headers);
			if (StringUtils.isNotBlank(params)) {
				StringEntity entity = new StringEntity(params, CHARSET_UTF8);
				entity.setContentEncoding(CHARSET_UTF8);
				entity.setContentType("application/json");// 发送json数据需要设置contentType
				post.setEntity(entity);
			}
			final CloseableHttpAsyncClient client = getHttpClient();
			client.execute(post, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse result) {
					try {
						HttpEntity entity = result.getEntity();
						String rslt = EntityUtils.toString(entity);
						logger.info("池化处理异步httpclient，post返回结果: {}", rslt);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						close(client); // 关闭连接
					}
				}

				@Override
				public void failed(Exception ex) {
					logger.error("http post请求失败", ex);
					close(client); // 失败时关闭连接
				}

				@Override
				public void cancelled() {
					close(client); // 服务取消时关闭连接
				}
			});
		} catch (Exception e) {
			logger.error("HTTP POST文本流异常:" + url + "," + JSON.toJSONString(params), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param map
	 *            参数容器
	 * @return String
	 */
	public static void doPost(String url, Map<String, String> map) {
		try {
			HttpPost post = new HttpPost(url);
			if (map != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 参数用
				for (Entry<String, String> entry : map.entrySet()) {
					params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				post.setEntity(new UrlEncodedFormEntity(params, CHARSET_UTF8));// 设置参数，及编码
			}
			final CloseableHttpAsyncClient client = getHttpClient();
			client.execute(post, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse result) {
					try {
						HttpEntity entity = result.getEntity();
						String rslt = EntityUtils.toString(entity);
						logger.info("池化处理异步httpclient，post返回结果: {}", rslt);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						close(client); // 关闭连接
					}
				}

				@Override
				public void failed(Exception ex) {
					logger.error("http post请求失败", ex);
					close(client); // 失败时关闭连接
				}

				@Override
				public void cancelled() {
					close(client); // 服务取消时关闭连接
				}
			});
		} catch (Exception e) {
			logger.error("HTTP POST异常:" + url + "," + JSON.toJSONString(map), e);
			throw new RuntimeException(e);
		}
	}

}
