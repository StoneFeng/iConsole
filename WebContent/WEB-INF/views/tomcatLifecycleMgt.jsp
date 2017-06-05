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
$('#tomcatLifecycleTableId').datagrid({
	title:'Tomcat',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/listAllTomcatsDg',
	method:'get',
	height:'auto',
	columns:[[
		{
			field:'hostDesc',
			title:'Tomcat'
		},
		{
			field:'host',
			title:'主机IP'
		},
		{
			field:'port',
			title:'端口号'
		},
		{
			field:'status',
			title:'状态',
			styler:function(val){
				if (val == 'STARTED') {
					return 'color:green;';
				} else {
					return 'color:red;';
				}
			}
		},
		{
			field:'start',
			title:'启动',
			align:'center',
			width:'60px',
			formatter:function(val, row, index){
				if ('STARTED' == row.status) {
					return "<a href='javascript:void(0)' class='startBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:startTomcat(\""+ row.jolokiaUrl +"\",\""+ row.hostDesc +"\")' class='startBtnCls'></a>";
				}
			}
		},
		{
			field:'stop',
			title:'停止',
			align:'center',
			width:'60px',
			formatter:function(val, row, index){
				if ('STOPPED' == row.status) {
					return "<a href='javascript:void(0)' class='stopBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:stopTomcat(\""+ row.jolokiaUrl +"\",\""+ row.hostDesc +"\")' class='stopBtnCls'></a>";
				}
			}
		}
	]],
	onLoadSuccess:function(data){
		$('.startBtnCls').linkbutton({text:'',plain:false,iconCls: 'icon-server_start'});
		$('.stopBtnCls').linkbutton({text:'',plain:false,iconCls: 'icon-server_stop'});
		$('#tomcatLifecycleTableId').datagrid('fixRowHeight');
	}
});
});
function startTomcat(jolokiaUrl, tomcat) {
	window.location.href = "startTomcat?tomcat=" + tomcat + "&jolokiaUrl="
			+ jolokiaUrl;
}
function stopTomcat(jolokiaUrl, tomcat) {
	window.location.href = "stopTomcat?tomcat=" + tomcat + "&jolokiaUrl="
			+ jolokiaUrl;
}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>

	<table id="tomcatLifecycleTableId"></table>
</body>
</html>