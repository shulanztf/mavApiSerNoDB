<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
	<a href="javascript:exportUserMoneyQueryXls()">JEECG导出</a>
</body>
</html>

<script type="text/javascript">
	//导出
	function exportUserMoneyQueryXls() {
		//alert(11);
		// JeecgExcelExport("general.do?exportHeartQueryXls", "zxbMoneyInRecList");
		 window.location.href = "general/exportHeartQueryXls.do";
		//window.location.href = "general.do?exportHeartQueryXls";
	}
	/**
	 * Jeecg Excel 导出
	 * 代入查询条件
	 */
	function JeecgExcelExport(url, datagridId) {
		var queryParams = $('#' + datagridId).datagrid('options').queryParams;
		$('#' + datagridId + 'tb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		var params = '&';
		$.each(queryParams, function(key, val) {
			params += '&' + key + '=' + val;
		});
		var fields = '&field=';
		$.each($('#' + datagridId).datagrid('options').columns[0], function(i,
				val) {
			if (val.field != 'opt') {
				fields += val.field + ',';
			}
		});
		window.location.href = url + encodeURI(fields + params);
	}

	function test() {
		$.ajax({
			//提交数据的类型 POST GET
			type : "POST",
			//提交的网址
			url : "testLogin.aspx",
			//提交的数据
			data : {
				Name : "sanmao",
				Password : "sanmaoword"
			},
			//返回数据的格式
			datatype : "html",//"xml", "html", "script", "json", "jsonp", "text".
			//在请求之前调用的函数
			beforeSend : function() {
				$("#msg").html("logining");
			},
			//成功返回之后调用的函数             
			success : function(data) {
				$("#msg").html(decodeURI(data));
			},
			//调用执行后调用的函数
			complete : function(XMLHttpRequest, textStatus) {
				alert(XMLHttpRequest.responseText);
				alert(textStatus);
				//HideLoading();
			},
			//调用出错执行的函数
			error : function() {
				//请求出错处理
			}
		});

	}
</script>
