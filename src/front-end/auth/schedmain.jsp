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
<style type="text/css">
	.onedaycell {border: 2px solid #CCC;
				font-size: 0.8em}
	.outermosttab {
					border: 1px solid black;
					font-family: Verdana, Sans-serif;
					margin-left: auto;
					margin-right: auto;
					}
	#toprow {border: 1px solid black;
			 font-size: 1.2em;
			 font-weight: normal;
			 color: #999}
	#toprow > th {border: 2px solid #CCC}
	.dateonly {
			font-size: 1.5em;
			color: #999
			}
	.monthcolcell {border: 2px solid #CCC;
				   font-size: 1.2em;
				   font-weight: bold;
				   text-align: center;
				   padding: 0 5px;
				   color: #999}
</style>
</head>
<body>
	<h1>DocSchedule</h1>
	<div id="maintable">Schedule will be displayed here</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/auth/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/auth/schedmain.js"></script>
	<br/>
	<div id="div2">
	<shiro:user>
	<table cellpadding="5" border="1">
		<tr>
			<td>
				User: <shiro:principal />
			</td>
			<td>
				<a href="<c:url value='/appmain.jsp' />">Home</a>
			</td>
			<td>
				<a href="<c:url value='/logout' />">Logout</a>
			</td>
		</tr>
	</table>
	</shiro:user>
	</div>
</body>
</html>