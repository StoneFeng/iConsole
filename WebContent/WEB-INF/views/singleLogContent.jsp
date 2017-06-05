<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志内容</title>
<script type="text/javascript">
	function selectCode() {
		var e = document.getElementById('p');
		if (document.all) {
			var r = document.selection.createRange();
			r.moveEnd("character", e.innerText.length);
			r.select()
		} else {
			var s = window.getSelection();
			var r = document.createRange();
			r.selectNodeContents(e);
			s.removeAllRanges();
			s.addRange(r);
		}
	}
</script>
</head>
<body>
	<div style="margin: 20px 0 10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#p').panel('expand',true)">展开</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#p').panel('collapse',true)">收起</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="selectCode();" id="copy_btn">全选</a>
	</div>
	<div id="p" class="easyui-panel" title="Log Details"
		style="width: 945px; height: auto; padding: 10px;">
		${logContent}</div>
</body>
</html>