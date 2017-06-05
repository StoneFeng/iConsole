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
$('#tomcatLogListTableId').datagrid({
	title:'Tomcat Logs',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/listTomcatLogsDg?jolokiaUrl=${jolokiaUrl}',
	method:'get',
	height:'auto',
	columns:[[
		{
			field:'logName',
			title:'日志文件名'
		},
		{
			field:'absolutePath',
			title:'日志文件路径'
		},
		{
			field:'logSize',
			title:'日志大小(KB)'
		},
		{
			field:'show',
			title:'日志查看',
			formatter:function(val, row, index){
				return "<a href='javascript:showLog(\""+ row.absolutePath +"\")' class='showBtnCls'></a>";
			}
		},
		{
			field:'clear',
			title:'日志擦除',
			formatter:function(val, row, index){
				return "<a href='javascript:clearLog(\""+ row.absolutePath +"\")' class='clearBtnCls'></a>";
			}
		},
		{
			field:'delete',
			title:'日志删除',
			formatter:function(val, row, index){
				return "<a href='javascript:deleteLog(\""+ row.absolutePath +"\")' class='deleteBtnCls'></a>";
			}
		}
		
	]],
	onLoadSuccess:function(data){
		$('.showBtnCls').linkbutton({text:'show',plain:false});
		$('.clearBtnCls').linkbutton({text:'clear',plain:false});
		$('.deleteBtnCls').linkbutton({text:'delete',plain:false});
		$('#tomcatLogListTableId').datagrid('fixRowHeight');
	}
});
});
function showLog(absPath) {
	window.location.href="printSingleLogFile?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
}
function clearLog(absPath) {
	var sure = confirm("Are you sure ?");
	if (sure == true) {
		window.location.href="clearLog?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
	}
}
function deleteLog(absPath) {
	var sure = confirm("Are you sure ?");
	if (sure == true) {
		window.location.href="deleteLog?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
	}
}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<table id="tomcatLogListTableId"></table>
</body>
</html>