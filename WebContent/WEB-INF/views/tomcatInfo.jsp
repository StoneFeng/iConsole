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
$('#tomcatInfoTableId').datagrid({
	title:'Web Modules',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/tomcatInfoDg',
	method:'get',
	height:'auto',
	columns:[[
		{
			field:'state',
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
			field:'contextPath',
			title:'上下文路径'
		},
		{
			field:'displayName',
			title:'显示名'
		},
		{
			field:'url',
			title:'URL',
			formatter:function(val, row, index){
				val = val.replace(/\/hawtio/, "");
				return '<a href="'+val+'">' + val + '</a>';
			}
		},
		{
			field:'startTime',
			title:'启动时间'
		},
		{
			field:'start',
			title:'启动',
			align:'center',
			width:'45px',
			formatter:function(val, row, index){
				if ('STARTED' == row.state) {
					return "<a href='javascript:void(0)' class='startBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:startWebApp(\""+ row.mbeanName +"\",\""+ row.jolokiaUrl +"\")' class='startBtnCls'></a>";
				}
			}
		},
		{
			field:'stop',
			title:'停止',
			align:'center',
			width:'45px',
			formatter:function(val, row, index){
				if ('STOPPED' == row.state) {
					return "<a href='javascript:void(0)' class='stopBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:stopWebApp(\""+ row.mbeanName +"\",\""+ row.jolokiaUrl +"\")' class='stopBtnCls'></a>";
				}
			}
		},
		{
			field:'uninstall',
			title:'卸载',
			align:'center',
			width:'45px',
			formatter:function(val, row, index){
				if ('STARTED' == row.state) {
					return "<a href='javascript:void(0)' class='uninstallBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:destroyWebApp(\""+ row.mbeanName +"\",\""+ row.jolokiaUrl +"\")' class='uninstallBtnCls'></a>";
				}
			}
		},
		{
			field:'reload',
			title:'重新载入',
			align:'center',
			width:'65px',
			formatter:function(val, row, index){
				return "<a href='javascript:reloadWebApp(\""+ row.mbeanName +"\",\""+ row.jolokiaUrl +"\")' class='reloadBtnCls'></a>";
			}
		},
//		{
//			field:'hawtioLog',
//			title:'Hawtio日志',
//			align:'center',
//			width:'80px',
//			formatter:function(val, row, index){
//				return "<a href='javascript:showTomcatLogs(\""+ row.jolokiaUrl +"\")' class='hawtioLogBtnCls'></a>";
//			}
//		},
		{
			field:'logList',
			title:'日志列表',
			align:'center',
			width:'75px',
			formatter:function(val, row, index){
				return "<a href='javascript:listTomcatLogs(\""+ row.jolokiaUrl +"\")' class='logListBtnCls'></a>";
			}
		}
	]],
	onLoadSuccess:function(data){
		$('.startBtnCls').linkbutton({text:'启动',plain:false});
		$('.stopBtnCls').linkbutton({text:'停止',plain:false});
		$('.uninstallBtnCls').linkbutton({text:'卸载',plain:false});
		$('.reloadBtnCls').linkbutton({text:'重新载入',plain:false});
		$('.hawtioLogBtnCls').linkbutton({text:'Hawtio日志',plain:false});
		$('.logListBtnCls').linkbutton({text:'日志列表',plain:false});
		$('#tomcatInfoTableId').datagrid('fixRowHeight');
	}
});
});
function startWebApp(mbeanName, jolokiaUrl) {
	window.location.href="startWebApp?mbeanName="+mbeanName+"&jolokiaUrl="+jolokiaUrl; 
}
function stopWebApp(mbeanName, jolokiaUrl) {
	window.location.href="stopWebApp?mbeanName="+mbeanName+"&jolokiaUrl="+jolokiaUrl; 
}
function destroyWebApp(mbeanName, jolokiaUrl) {
	$.messager.confirm('Confirm','确定要卸载应用 ?',function(b){
	    if (b){
	    	window.location.href="destroyWebApp?mbeanName="+mbeanName+"&jolokiaUrl="+jolokiaUrl;
	    }
	});
}
function reloadWebApp(mbeanName, jolokiaUrl) {
	window.location.href="reloadWebApp?mbeanName="+mbeanName+"&jolokiaUrl="+jolokiaUrl; 
}
function showTomcatLogs(jolokiaUrl) {
	window.location.href="tomcatLog?jolokiaUrl="+jolokiaUrl; 
}
function listTomcatLogs(jolokiaUrl) {
	window.location.href="listTomcatLogs?jolokiaUrl="+jolokiaUrl; 
}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<h6 style="color:red">*配置WEB_MODULE_FILTER可过滤应用*</h6>
	<table id="tomcatInfoTableId"></table>
</body>
</html>