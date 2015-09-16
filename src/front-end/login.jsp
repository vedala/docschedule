<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DocSchedule Login</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
<style type="text/css">
	#logintable {
		margin-top: 100px;
		margin-left: auto;
		margin-right: auto;
		}
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
					<shiro:user><a href="<c:url value='/appmain.jsp' />">Home</a></shiro:user>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</div>
		<div id="mainbody">
			<div id="loginform">
			<form action="" method="post">
				<table id="logintable">
					<tr>
						<td>Username:</td>
						<td><input type="text" name="username" maxlength="50" /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password" maxlength="50" /></td>
					</tr>
					<tr>
						<td colspan="2" align="right"><input type="submit" name="submit" value="Login" /></td>
					</tr>
				</table>
			</form>
			</div>
		</div>
	</div>
</body>
</html>