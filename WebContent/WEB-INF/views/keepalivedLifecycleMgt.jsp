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
$('#keepalivedLifecycleTableId').datagrid({
	title:'Keepalived',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/listAllKeepalivedDg',
	method:'get',
	height:'auto',
	columns:[[
		{
			field:'hostDesc',
			title:'主机描述'
		},
		{
			field:'host',
			title:'主机IP'
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
			formatter:function(val,row,index){
				debugger;
				if ('STARTED' == row.status) {
					return "<a href='javascript:void(0)' class='startBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:startKeepalived(\""+ row.jolokiaUrl +"\")' class='startBtnCls'></a>";
				}
			}
		},
		{
			field:'stop',
			title:'停止',
			align:'center',
			width:'60px',
			formatter:function(val,row,index){
				if ('STOPPED' == row.status) {
					return "<a href='javascript:void(0)' class='stopBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:stopKeepalived(\""+ row.jolokiaUrl +"\")' class='stopBtnCls'></a>";
				}
			}
		}
	]],
	onLoadSuccess:function(data){
		$('.startBtnCls').linkbutton({text:'启动',plain:false});
		$('.stopBtnCls').linkbutton({text:'停止',plain:false});
		$('#keepalivedLifecycleTableId').datagrid('fixRowHeight');
	}
});
});
function startKeepalived(jolokiaUrl) {
	window.location.href = "startKeepalived?jolokiaUrl="
			+ jolokiaUrl;
}
function stopKeepalived(jolokiaUrl) {
	window.location.href = "stopKeepalived?jolokiaUrl="
			+ jolokiaUrl;
}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>

	<table id="keepalivedLifecycleTableId"></table>
</body>
</html>