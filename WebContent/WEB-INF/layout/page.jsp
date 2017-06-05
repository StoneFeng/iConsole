<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>iConsole</title>
<!-- <style>
body {
	margin-top: 20px;
	margin-bottom: 20px;
	background-color: #DFDFDF;
}
</style>-->
</head>
<body>
	<!-- <table>
		<tr>
			<td colspan="2"><tiles:insertAttribute name="header" /></td>
		</tr>
		<tr>
			<td><tiles:insertAttribute name="menu" /></td>
			<td><tiles:insertAttribute name="body" /></td>
		</tr>
		<tr>
			<td colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>-->
	<table border="0" cellpadding="2" cellspacing="2" align="center">
       <tr>
           <td height="30" colspan="2"><tiles:insertAttribute name="header" />
           </td>
       </tr>
       <tr>
           <td height="250" width="200"><tiles:insertAttribute name="menu" /></td>
           <td width="600"><tiles:insertAttribute name="body" /></td>
       </tr>
       <tr>
           <td height="30" colspan="2"><tiles:insertAttribute name="footer" />
           </td>
       </tr>
   </table>
</body>
</html>