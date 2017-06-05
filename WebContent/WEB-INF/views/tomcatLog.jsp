<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
$(function() {
$('#tomcatLogTableId').datagrid({
	title:'Tomcat',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/tomcatLogsDg?jolokiaUrl=${jolokiaUrl}',
	method:'get',
	height:'auto',
	columns:[[
		{
			field:'timestamp',
			title:'时间戳'
		},
		{
			field:'logLevel',
			title:'日志级别',
			styler:function(val, row, index){
				if (val == 'ERROR') {
					return 'color:red;';
				}
			}
		},
		{
			field:'logger',
			title:'日志器'
		},
		{
			field:'message',
			title:'日志信息',
			styler:function(val, row, index){
				if (row.logLevel == 'ERROR') {
					return 'color:red;';
				}
			}
		}
		
	]],
	onLoadSuccess:function(data){
		$('#tomcatLogTableId').datagrid('fixRowHeight');
	}
});
});
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<table id="tomcatLogTableId"></table>
</body>
</html>