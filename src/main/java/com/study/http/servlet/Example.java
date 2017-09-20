package com.study.http.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.web.http.HttpSessionManager;

/**
 * 
 * @Title: Example
 * @Description:模拟servlet
 * @see http://blog.163.com/_kid/blog/static/30405476201632334644812/
 * @Author: zhaotf
 * @Since:2017年9月20日 下午2:29:20
 * @Version:1.0
 */
// @WebServlet("/example") 与springMVC冲突
public class Example extends HttpServlet {
	private static final long serialVersionUID = -4200977787278264715L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * 在请求中，根据名为org.springframework.session.web.http.HttpSessionManager的key
		 * 获得Spring Session session管理器的引用
		 */
		HttpSessionManager sessionManager = (HttpSessionManager) request
				.getAttribute("org.springframework.session.web.http.HttpSessionManager");

		/*
		 * 使用session管理器找出所请求session的别名。 默认情况下，session别名会包含在url中，并且请求参数的名称为“_s”。
		 * 例如，http://localhost:8080/example?_s=1 将会使如下的代码打印出“Requested Session
		 * Alias is: 1”
		 */
		String requestedSessionAlias = sessionManager.getCurrentSessionAlias(request);
		System.out.println("Requested Session Alias is:  " + requestedSessionAlias);

		/*
		 * 返回一个唯一的session别名id，这个别名目前没有被浏览器用来发送请求。 这个方法并不会创建新的session，
		 * 我们需要调用request.getSession()来创建新session。
		 */
		String newSessionAlias = sessionManager.getNewSessionAlias(request);

		/*
		 * 使用新创建的session别名来建立URL，这个URL将会包含 “_s”参数。例如，如果newSessionAlias的值为2的话，
		 * 那么如下的方法将会返回“/inbox?_s=2”
		 */

		String encodedURL = sessionManager.encodeURL("/inbox", newSessionAlias);
		System.out.println(encodedURL);

		/*
		 * 返回session别名与session id所组成的Map， 它们是由浏览器发送请求所形成的。
		 */
		Map<String, String> sessionIds = sessionManager.getSessionIds(request);
	}
}
