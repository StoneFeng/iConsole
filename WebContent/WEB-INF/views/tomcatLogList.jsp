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
	singleSelect:false,
	url:'${ctx}/listTomcatLogsDg?jolokiaUrl=${jolokiaUrl}',
	method:'get',
	height:'auto',
	toolbar:toolbar,
	columns:[[
		{
			field:'logName',
			title:'日志文件名',
			checkbox:true
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
				if ('catalina.out' == row.logName) {
					return "<a href='javascript:deleteLog(\""+ row.absolutePath +"\")' class='deleteBtnCls' disabled='disabled'></a>";
				} else {
					return "<a href='javascript:deleteLog(\""+ row.absolutePath +"\")' class='deleteBtnCls'></a>";
				}
			}
		}
		
	]],
	onSelectAll:function(rows){
		$('#batchRemoveBtn').linkbutton('enable');
	},
	onUnselectAll:function(rows){
		$('#batchRemoveBtn').linkbutton('disable');
	},
	onSelect:function(index,row){
		$('#batchRemoveBtn').linkbutton('enable');
	},
	onUnselect:function(index,row){
		var rows = $('#tomcatLogListTableId').datagrid('getSelections');
		if (rows.length == 0) {
			$('#batchRemoveBtn').linkbutton('disable');
		}
	},
	onLoadSuccess:function(data){
		$('.showBtnCls').linkbutton({text:'show',plain:false});
		$('.clearBtnCls').linkbutton({text:'clear',plain:false});
		$('.deleteBtnCls').linkbutton({text:'delete',plain:false});
		$('#batchRemoveBtn').linkbutton('disable');
		$('#tomcatLogListTableId').datagrid('fixRowHeight');
	}
});
});
function showLog(absPath) {
	window.location.href="printSingleLogFile?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
}
function clearLog(absPath) {
	$.messager.confirm('Confirm','确定要擦除日志 ?',function(b){
	    if (b){
	    	window.location.href="clearLog?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
	    }
	});
}
function deleteLog(absPath) {
	$.messager.confirm('Confirm','确定要删除日志 ?',function(b){
	    if (b){
	    	window.location.href="deleteLog?path="+absPath+"&jolokiaUrl=${jolokiaUrl}";
	    }
	});
}

	var toolbar = [ {
		id : 'batchRemoveBtn',
		text : '批量删除',
		iconCls : 'icon-remove',
		handler : function() {
			$.messager.confirm('Confirm','确定要删除选中日志 ?',function(b){
			    if (b){
					debugger;
					var ss = [];
			    	var rows = $('#tomcatLogListTableId').datagrid('getSelections');
			    	for(var i=0; i<rows.length; i++){
						var row = rows[i];
						if ('catalina.out' == row.logName) {
							$.messager.alert('Error','不能删除catalina.out !','error');
							return;
						}
						ss.push(row.absolutePath);
					}
			    	window.location.href="deleteLogBatch?path="+ss+"&jolokiaUrl=${jolokiaUrl}";
			    }
			});
		}
	} ];
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<table id="tomcatLogListTableId"></table>
</body>
</html>