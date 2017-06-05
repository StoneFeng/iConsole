<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery-easyui-1.5/themes/default/easyui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery-easyui-1.5/themes/icon.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/js/jquery-easyui-1.5/demo/demo.css'/>">
<script type="text/javascript"
	src="<c:url value='/js/jquery-easyui-1.5/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery-easyui-1.5/jquery.easyui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js'/>"></script>
<script type="text/javascript">
	function gotoHawtio(hawtioUrl) {
		window.open(hawtioUrl, "_blank");
	}
	function gotoOsMaster(o_s_m) {
		window.open(o_s_m, "_blank");
	}
	function gotoOsBackup(o_s_b) {
		window.open(o_s_b, "_blank");
	}
	function gotoOqMaster(o_q_m) {
		window.open(o_q_m, "_blank");
	}
	function gotoOqBackup(o_q_b) {
		window.open(o_q_b, "_blank");
	}
	function gotoIsMaster(i_s_m) {
		window.open(i_s_m, "_blank");
	}
	function gotoIsBackup(i_s_b) {
		window.open(i_s_b, "_blank");
	}
	function gotoIqMaster(i_q_m) {
		window.open(i_q_m, "_blank");
	}
	function gotoIqBackup(i_q_b) {
		window.open(i_q_b, "_blank");
	}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="Menu" style="width: 150px;">
		<div class="easyui-menu" data-options="inline:true"
			style="width: 100%">
			<div onclick="gotoHawtio('${hawtioUrl}')">
				<%-- TODO:考虑使用spring国际化标签避免硬编码 --%>
				<span><b>Hawtio</b></span>
			</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-chart_bar'" onclick="javascript:window.location.href='osRes'">
				<span><b>系统资源</b></span>
			</div>
			<div class="menu-sep"></div>
			<div onclick="javascript:window.location.href='osResAlarm'" data-options="iconCls:'icon-bell'">
				<span><b>系统资源告警</b></span>
			</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-server'">
				<span><b>Tomcat</b></span>
				<div>
					<div data-options="iconCls:'icon-information'" onclick="javascript:window.location.href='tomcatInfo'">Tomcat信息</div>
					<div data-options="iconCls:'icon-server_start'" onclick="javascript:window.location.href='listAllTomcats'">Tomcat启动/停止</div>
				</div>
			</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-server'" onclick="javascript:window.location.href='listAllHttpds'">
				<span><b>HTTPD</b></span>
			</div>
			<div class="menu-sep"></div>
			<div onclick="javascript:window.location.href='listAllKeepalived'">
				<span><b>Keepalived</b></span>
			</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-time'" onclick="javascript:window.location.href='getQuartzJobStatus'">
				<span><b>定时调度任务管理</b></span>
			</div>
			<div class="menu-sep"></div>
			<div>
				<span><b>Apache监控工具</b></span>
				<div>
					<div>
						<span><b>外网慢通道</b></span>
						<div>
							<div onclick="gotoOsMaster('${o_s_m}')">主</div>
							<div onclick="gotoOsBackup('${o_s_b}')">备</div>
						</div>
					</div>
					<div>
						<span><b>外网快通道</b></span>
						<div>
							<div onclick="gotoOqMaster('${o_q_m}')">主</div>
							<div onclick="gotoOqBackup('${o_q_b}')">备</div>
						</div>
					</div>
					<div>
						<span><b>内网慢通道</b></span>
						<div>
							<div onclick="gotoIsMaster('${i_s_m}')">主</div>
							<div onclick="gotoIsBackup('${i_s_b}')">备</div>
						</div>
					</div>
					<div>
						<span><b>内网快通道</b></span>
						<div>
							<div onclick="gotoIqMaster('${i_q_m}')">主</div>
							<div onclick="gotoIqBackup('${i_q_b}')">备</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>