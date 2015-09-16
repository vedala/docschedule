<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DocSchedule Page Not Found</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
<style>
	#mainbody {
		margin-top: 50px;
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
					<td>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td>
					<a href="<c:url value='/appmain.jsp' />">Home</a>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</div>
		<div id="mainbody">
			<h2>Error</h2>
			<p>The page that you requested is not found or some other error occurred on server.</p>
		</div>
	</div>
</body>
</html>