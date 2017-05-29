<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>BootStarp 学习</title>

<link rel="stylesheet"
	href="<%=basePath%>plug-in/dist/css/bootstrap.css">
<script type="text/javascript"
	src="<%=basePath%>js/jquery1.8.0/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>plug-in/dist/js/bootstrap.js"></script>

</head>
<body>
<h3>springMVC 页面跳转</h3>
</body>
</html>