<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据报表，锁定表头</title>
	<link rel="stylesheet" type="text/css" href="../plug-in/jquery-easyui-1.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../plug-in/jquery-easyui-1.5/themes/icon.css" />
	<style type="text/css">
		.datagrid-header-rownumber,.datagrid-cell-rownumber{
			width:35px;
		}
	</style>
	<script type="text/javascript" src="../js/jquery1.8.0/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="../plug-in/jquery-easyui-1.5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/datagrid-bufferview.js"></script>
	<script type="text/javascript" src="../plug-in/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
	
</head>
<body>
	<h2>样片库管理</h2>
	
	<div style="padding:8px;height:auto">
	    参数项名称: <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
	    创建时间: <input class="easyui-datebox" name="createTime" style="width:80px">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-search">查找</a>
	    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
	</div>
	<table id="tt" class="easyui-datagrid" style="width:910px;height:350px"
	       title="参数项列表" iconCls="icon-save"
	       rownumbers="true" pagination="true">
	</table>


<script type="text/javascript">

    $('#tt').datagrid({
        title: "参数项列表",
        url: "../generalController/findList.htm",
        pageSize:5,
        queryParams:{
        	//"page":1,
        	//"rows":15
        },
        columns: [
            [
                {field: 'id', title: '参数ID', width: 180, align: "center"},
                {field: 'name', title: '参数名称', width: 180, align: "center"},
                {field: 'totalmoney', title: '标签', width: 180, align: 'center'},
                {field: 'inserttime', title: '创建时间', width: 180, align: "center"}
            ]
        ], toolbar: [
            {
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    openDialog("add_dialog", "add");
                }
            },
            '-',
            {
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    openDialog("add_dialog", "edit");
                }
            },
            '-',
            {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    delAppInfo();
                }
            }
        ]
    });

    //设置分页控件
    var p = $('#tt').datagrid('getPager');
    p.pagination({
        pageSize: 5,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
    });
</script>
</body>
</html>