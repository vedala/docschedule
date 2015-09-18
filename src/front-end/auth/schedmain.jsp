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
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
<style type="text/css">
	.onedaycell {border: 2px solid #CCC;
				font-size: 0.8em}
	.outermosttab {
					border: 1px solid black;
					font-family: Verdana, Sans-serif;
					margin-left: auto;
					margin-right: auto;
					margin-bottom: 30px;
					}
	#toprow {border: 1px solid black;
			 font-size: 1.2em;
			 font-weight: normal;
			 color: #CC5500}
	#toprow > th {border: 2px solid #CCC}
	.dateonly {
			font-size: 1.5em;
			color: #CC5500
			}
	.monthcolcell {border: 2px solid #CCC;
				   font-size: 1.2em;
				   font-weight: bold;
				   text-align: center;
				   padding: 0 5px;
				   color: #CC5500}
</style>
</head>
<body>
	<div id="fulldiv">
		<div id="header">
		<table>
			<tr>
				<td id="logo" valign="top" width="60%">
				DocSchedule
				</td>
				<td width="20%">
				</td>
				<td width="20%">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
				</td>
				<td id="userdisp">
					<shiro:user>User: <shiro:principal /></shiro:user>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<shiro:user><a href="<c:url value='/appmain.jsp' />">Home</a></shiro:user>
				</td>
				<td id="loginoutlink">
					<shiro:user><a href="<c:url value='/logout' />">Logout</a></shiro:user>
				</td>
			</tr>
		</table>
		</div>
		<div id="mainbody">
		<div id="maintable">Schedule will be displayed here</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/auth/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/auth/schedmain.js"></script>
		</div>
	</div>
</body>
</html>