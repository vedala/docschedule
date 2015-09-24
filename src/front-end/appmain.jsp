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
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
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
				<shiro:user><a href="<c:url value='/auth/schedmain.jsp'/>">View Schedule</a></shiro:user>
			</td>
			<td id="loginoutlink">
				<shiro:guest><a href="<c:url value='/login.jsp' />">Login</a></shiro:guest>
				<shiro:user><a href="<c:url value='/logout' />">Logout</a></shiro:user>
			</td>
		</tr>
	</table>
	</div>
<div id="mainbody">
<h3>Login</h3>
<ul>
<li>Login using link on top-right and click on "View Schedule" link that
appears.</li>
<li>Username/password: <b>appuser99/secret99</b></li>
</ul>

<h3>Overview</h3>

<p>The DocSchedule application can be used to generate
schedule for hospitalist physician groups.
A hospitalist physician group usually provides 24x7x365 care
to admitted patients at a hospital.</p>

<h3>A Basic Scenario</h3>

<p>Following scenario will help understand the application better:</p>

<ul>
<li>The hospitalist group consists of eight physicians.</li>
<li>The group is divided into two teams of four physcians
  each (Let's call the teams Team-A and Team-B).</li>
<li>Each team works 7 days straight starting on a Tuesday. During
  the 7 days that Team-A is working, Team-B does not work and
  vice-versa.</li>
<li>For example, Team-A works from 6-July-2015 to 13-July-2015. Then,
  Team-B works from 14-July-2015 to 20-July-2015. And so on.</li>
<li>On any given day, four physicians work at the hospital,
  three on Day shift and one on Night shift.</li>
<li>Day shift is 7am to 7pm. Night shift is 7pm to 7am.</li>
</ul>

<h3>Features in the Current Version</h3>
<ul>
<li>The application is currently in early stages of development, so very
  basic functionality is available.</li>
<li>Upon login, schedule for 6 weeks is displayed (starting from current week).</li>
<li>Schedule page can be accessed only by authenticated users.</li>
</ul>

<h3>Planned Features</h3>
<ul>
<li>Ability to scroll schedule forward and backward, so user can view
more than six weeks' worth of schedule.</li>
<li>Ability to generate schedule. This feature will enable group director
  to create schedule for a period of time (usually a few months) based
  or pre-set rules. The pre-set rules are similar to rules described in
  the Basic Scenario above. This feature will be authorized only for
  the director.</li>
<li>Ability to modify rules. Provide interface to enable the director
  to modify rules. This feature only authorized for the director.</li>
<li>Add SSL support.</li>
<li>Ability to Sign-Up users from website.</li>
<li>Enhance application to support multiple tenants.</li>
</ul>
  
<h3>Technical Note</h3>
<ul>
<li>The application is developed using Java, Apache Tomcat, MySQL.</li>
<li>Apache Shiro is used for user authentication.</li>
<li>Front-end is written mainly in basic HTML, CSS and JSP.</li>
<li>jQuery is the only front-end library used.</li>
<li>Data is fetched from the back-end via a call to REST web service.
  This service is developed in Java using Jersey REST library and
  Jackson library for JSON conversion.</li>
<li>Front-end uses Ajax to make a call to the web service and schedule
  display is generated using JavaScript.</li>
</ul>
</div>
</div>
</body>
</html>