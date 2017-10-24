package com.study.nio.httpasync;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @Title: NioHttpAsyncClientDemo
 * @Description:NIO/Httpclient 整合
 * @see http://www.cnblogs.com/zemliu/p/3719292.html
 * @see http://blog.csdn.net/xiaoxian8023/article/details/49949813
 * @Author: zhaotf
 * @Since:2017年10月23日 下午1:22:18
 * @Version:1.0
 */
public class NioHttpAsyncClientDemo {

	private static final String encoding = "UTF-8";
	private Random rand = new Random();

	public static void main(String[] args) throws Exception {
		NioHttpAsyncClientDemo demo = new NioHttpAsyncClientDemo();
		String url = "http://127.0.0.1:8080/spring-boot-tomcat-jsp/hmBase/postNioHttpAsync.do";
		// 装填参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "js");
		map.put("city", System.currentTimeMillis() + "上海");
		demo.postNio(url, map, new RoakbalkHanlder() {

			@Override
			public void roalbck(String param) {
				System.out.println("上海接口AIP回调" + param);
			}
		});
		System.out.println(":执行结果:" + map.toString());

		map.put("city", System.currentTimeMillis() + "北京");
		demo.postNio(url, map, new RoakbalkHanlder() {

			@Override
			public void roalbck(String param) {
				System.out.println("北京接口AIP回调" + param);
			}
		});
		System.out.println(":执行结果:" + map.toString());

		map.put("city", System.currentTimeMillis() + "青岛");
		demo.postNio(url, map, new RoakbalkHanlder() {

			@Override
			public void roalbck(String param) {
				System.out.println("青岛接口AIP回调" + param);
			}
		});
		System.out.println(":执行结果:" + map.toString());

		map.put("city", System.currentTimeMillis() + "半碗");
		demo.postNio(url, map, new RoakbalkHanlder() {

			@Override
			public void roalbck(String param) {
				System.out.println("半碗接口AIP回调" + param);
			}
		});
		System.out.println(":执行结果:" + map.toString());

		map.put("city", System.currentTimeMillis() + "灰可");
		demo.postNio(url, map, new RoakbalkHanlder() {

			@Override
			public void roalbck(String param) {
				System.out.println("灰可接口AIP回调" + param);
			}
		});
		System.out.println(":执行结果:" + map.toString());

		// ExecutorService es = Executors.newCachedThreadPool();
		// for (int i = 0; i < 100; i++) {
		// es.submit(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("code", "js");
		// map.put("city", Thread.currentThread().getId() + "上海");
		// System.out.println(Thread.currentThread().getId() + ":待发送信息:" +
		// map.toString());
		//// demo.postNio(url, map);
		//// demo.postNio2(url, map);
		// System.out.println(Thread.currentThread().getId() + ":执行结果:" +
		// map.toString());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// }

		System.out.println("任务分发完毕....");
		Scanner sca = new Scanner(System.in);
		while (!"q".equals(sca.nextLine())) {
			Thread.sleep(1000 * 10);
		}
		// es.shutdown();
		demo.stopHttp();
		sca.close();
		System.out.println("测试结束......");
	}

	private static CloseableHttpAsyncClient httpAsyncClient;

	public NioHttpAsyncClientDemo() throws IOReactorException {
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
		PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
		cm.setMaxTotal(100);
		httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
		httpAsyncClient.start();
	}

	/**
	 * http/nio post请求,非阻塞式
	 * 
	 * void
	 * 
	 * @throws IOReactorException
	 * @throws UnsupportedEncodingException
	 */
	public void postNio(String url, final Map<String, String> map, final RoakbalkHanlder hand) throws Exception {
		final HttpPost httpPost = new HttpPost(url);
		// 设置参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		httpAsyncClient.execute(httpPost, new FutureCallback<HttpResponse>() {

			// 处理
			public void completed(final HttpResponse response) {
				System.out.println(httpPost.getRequestLine() + "==>" + response.getStatusLine());

				InputStream instream = null;
				HttpEntity entity = response.getEntity();
				String body = "";
				try {
					// 这里使用EntityUtils.toString()方式时会大概率报错，原因：未接受完毕，链接已关
					instream = entity.getContent();
					if (entity != null) {
						StringBuilder sb = new StringBuilder();
						char[] tmp = new char[1024];
						Reader reader = new InputStreamReader(instream, encoding);
						int l;
						while ((l = reader.read(tmp)) != -1) {
							sb.append(tmp, 0, l);
						}
						body = sb.toString();
						map.put("rslt", body);
						// execuRslt(body);
						hand.roalbck(body);

						// TODO 事务模拟
						if (rand.nextBoolean()) {
							throw new Exception("随机异常……");
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (instream != null) {
							instream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						EntityUtils.consume(entity);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getId() + "http/nio整合，返回结果:" + body);

			}

			public void failed(final Exception ex) {
				System.out.println(httpPost.getRequestLine() + "-->" + ex);
			}

			public void cancelled() {
				System.out.println(httpPost.getRequestLine() + " cancelled");
			}
		});

	}

	/**
	 * 阻塞式
	 * 
	 * @param url
	 * @param map
	 * @throws Exception
	 *             void
	 */
	@Deprecated
	public void postNio2(String url, Map<String, String> map) throws Exception {
		final HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		Future<HttpResponse> fu = httpAsyncClient.execute(httpPost, new FutureCallback<HttpResponse>() {

			// 处理
			public void completed(final HttpResponse response) {
				System.out.println(httpPost.getRequestLine() + "==>" + response.getStatusLine());
			}

			public void failed(final Exception ex) {
				System.out.println(httpPost.getRequestLine() + "-->" + ex);
			}

			public void cancelled() {
				System.out.println(httpPost.getRequestLine() + " cancelled");
			}
		});

		// 在execute方法外,变成阻塞式
		InputStream instream = null;
		HttpEntity entity = fu.get().getEntity();
		String body = "";
		try {
			// 这里使用EntityUtils.toString()方式时会大概率报错，原因：未接受完毕，链接已关
			instream = entity.getContent();
			if (entity != null) {
				StringBuilder sb = new StringBuilder();
				char[] tmp = new char[1024];
				Reader reader = new InputStreamReader(instream, encoding);
				int l;
				while ((l = reader.read(tmp)) != -1) {
					sb.append(tmp, 0, l);
				}
				body = sb.toString();
				map.put("rslt", body);
				// execuRslt(body);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getId() + "http/nio整合，返回结果:" + body);
	}

	public void execuRslt(String rslt) {
		System.out.println(Thread.currentThread().getId() + ":回调:" + rslt);
	}

	/**
	 * 停止http
	 * 
	 * void
	 */
	public void stopHttp() {
		try {
			if (httpAsyncClient != null) {
				httpAsyncClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description:回调函数
	 */
	public interface RoakbalkHanlder {

		/**
		 * 业务代码写在这里
		 * 
		 * @param param
		 */
		public void roalbck(String param);

	}

}
