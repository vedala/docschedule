<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DocSchedule Application</title>
</head>
<body>
<h1>DocSchedule</h1>
<p>DocSchedule is an application for generating and viewing physician schedule. The application's primary focus is scheduling for Hospitalist physician groups.</p>
<p><shiro:guest>Login to view schedule: <a href="login.jsp">Login</a></shiro:guest>
<shiro:user>You are logged in. Visit schedule page: <a href="<c:url value='/auth/schedmain.jsp'/>">Schedule</a></shiro:user></p>
</body>
</html>