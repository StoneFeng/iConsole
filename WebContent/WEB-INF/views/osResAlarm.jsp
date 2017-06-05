<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统资源告警</title>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<ul class="easyui-datalist" title="系统资源告警信息" lines="true"
		style="width: auto; height: 250px">
		<c:forEach items="${msgList}" var="item">
			<li value="${item}">${item}</li>
		</c:forEach>
	</ul>
</body>
</html>