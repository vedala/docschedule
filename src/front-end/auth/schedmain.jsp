<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DocSchedule Schedule Display</title>
</head>
<body>
	<h1>DocSchedule</h1>
	<div id="maintable">Schedule will be displayed here</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/auth/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/auth/schedmain.js"></script>
	<br/>
	<div id="div2">
	<table cellpadding="5" border="1">
		<tr>
			<td>
				User: <shiro:principal />
			</td>
			<td>
				<a href="<c:url value='/appmain.jsp' />">Home</a>
			</td>
			<td>
				<a href="<c:url value='/LogoutUser' />">Logout</a>
			</td>
		</tr>
	</table>
	</div>
</body>
</html>