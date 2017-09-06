<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();  
	String basePath = request.getScheme() + "://"  
        + request.getServerName() + ":" + request.getServerPort()  
        + path + "/";  
          
    request.setCharacterEncoding("utf-8");  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>首页</title>
<script type="text/javascript" src="http://localhost:8080/mavApiSerNoDB/js/jquery1.8.0/jquery-1.8.0.js"></script>
<script type="text/javascript" src="http://localhost:8080/mavApiSerNoDB/js/jquery1.8.0/jquery-1.8.0.min.js"></script>
</head>
<body>
	<h2>根目录，你好!</h2>
	
	<a id="a_post" name="ddaa" href="#" onclick="dataTest()">数据绑定测试POST</a>
	<br>
	<a id="a_obj_post" name="objaaa" href="#" onclick="postObjTest()">对象数据绑定测试POST</a>
	<br>
	<a is="a_get" name="cccee" href="#" onclick="getDataTest()">数据绑定测试GET</a>
	
</body>

<script>
function dataTest() {
	//alert($("#a_post").attr("name"));
	
	$.ajax({
	   url: "/mavApiSerNoDB/general/postDataTest.do",
	   type: "POST",
	   data: {msg:"abc3a不在中方", pageNo:3, filg:true},
	   //dataType:"json",
	   //contentType: "application/json; charset=utf-8", 
	   success: function(msg){
	     alert( "返回结果: " + msg );
	   }
	});
}

function postObjTest() {
	$.ajax({
	   url: "/mavApiSerNoDB/general/postObjTest.do",
	   type: "POST",
	   data: {name:"e不e", age:3, good:true},
	   //dataType:"json",
	   //contentType: "application/json; charset=utf-8", 
	   success: function(msg){
	     alert( "返回结果: " + msg );
	   }
	});
}

function getDataTest() {
	//alert(333);
	$.ajax({
	   url: "http://localhost:8080/mavApiSerNoDB/general/getDataTest.do?msg=a不a",
	   type: "GET",
	   data: {msg:"c方c", pageNo:3, filg:true},
	   //dataType:"JSONP",
	   //contentType: "application/json; charset=utf-8",
	   success: function(msg){
	     alert( "返回结果: " + msg );
	   }
	});
}
</script>
</html>
