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
	setInterval(function show() {
		$('#osResTableId').datagrid('load',{});
	}, 3000);
});
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<table id="osResTableId" class="easyui-datagrid"
		title="系统资源"
		data-options="rownumbers:true,url:'${ctx}/osResAjax',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'hostDesc'">主机描述</th>
				<th data-options="field:'host'">主机IP</th>
				<th data-options="field:'cpu'">CPU使用率(%)</th>
				<th data-options="field:'ram'">可用内存(G)</th>
			</tr>
		</thead>
	</table>
</body>
</html>