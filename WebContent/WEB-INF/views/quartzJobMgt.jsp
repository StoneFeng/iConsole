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
$('#quartzJobMgtTableId').datagrid({
	title:'定时调度管理',
	rownumbers:true,
	singleSelect:true,
	url:'${ctx}/listAllQuartzJobDg',
	method:'get',
	fit:true,
	autoRowHeight:false,//设为false提高table加载性能
	columns:[[
		{
			field:'jobName',
			title:'任务名'
		},
		{
			field:'switcher',
			title:'开关',
			//align:'center',
			//width:'60px',
			formatter:function(val,row,index){
				debugger;
				console.log("val: " + val);
				if (val) {
					return "<input type='checkbox' onchange='javascript:changeQuartzJobSwitcher(\""+ row.jobName +"\")' checked='checked' id=\""+ row.jobName +"\" />";
				} else {
					return "<input type='checkbox' onchange='javascript:changeQuartzJobSwitcher(\""+ row.jobName +"\")' id=\""+ row.jobName +"\" />";
				}
			}
		}
	]]
});
});
function changeQuartzJobSwitcher() {
	debugger;
	var scanProcStatusJob = document.getElementById("SelfMaintenance").checked;
	var scanResAvailabilityJob = document.getElementById("OsResourceAlarm").checked;
	var osResourceRecordJob = document.getElementById("OsResourceRecord").checked;
	console.log("SelfMaintenance: " + scanProcStatusJob);
	console.log("OsResourceAlarm: " + scanResAvailabilityJob);
	window.location.href="changeQuartzJobSwitcher?scanProcStatusJobStr="+scanProcStatusJob+"&scanResAvailabilityJobStr="+scanResAvailabilityJob+"&osResourceRecordJobStr="+osResourceRecordJob;
}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>

	<table id="quartzJobMgtTableId"></table>
</body>
</html>