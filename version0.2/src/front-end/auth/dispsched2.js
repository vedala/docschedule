var HttpClient = function() {    this.get = function(aUrl, aCallback) {        var anHttpRequest = new XMLHttpRequest();        anHttpRequest.onreadystatechange = function() {             if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)                aCallback(anHttpRequest.responseText);        }        anHttpRequest.open( "GET", aUrl, true );                    anHttpRequest.send( null );    }}// var dateStr = "2015-08-06";// var dateObj = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));var dateObj = new Date();var dayOfWeek = dateObj.getDay();if (dayOfWeek != 6) {	dateObj.setDate(dateObj.getDate() - dayOfWeek);}var startDate = dateObj.toISOString().substr(0, 10);dateObj.setDate(dateObj.getDate() + 6);var endDate = dateObj.toISOString().substr(0, 10);aClient = new HttpClient();// document.getElementById("dv1").innerHtml = "-----";var div1 = document.getElementById("dv1");div1.innerHTML = '<table cellpadding="5" border="1">';var url = "http://localhost:8080/docsched/api/v1/dailyschedule?startdate="			+ startDate + "&" + endDate;/*aClient.get(url, writeWeek);*/	div1.innerHTML += "<tr>";	div1.innerHTML += "<td>";	div1.innerHTML += "123123123123";	div1.innerHTML += "</td>";	div1.innerHTML += "</tr>";div1.innerHTML += "</table>";document.close();function writeWeek(response) {var obj = JSON.parse(response);document.write("<tr>");for(i = 0; i < 7; i++){	var dateStr = obj[i].scheduleDate;return "string-returned-by-write-week";	document.write("<td>");	document.write('<table cellpadding="5" border="1">');	document.write("<tr>");	document.write("<td>");	document.write(dateStr.substr(8,2));	document.write("</td>");	document.write("<td>");	var dateObj2 = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));	var dayOfWeekStr = dayOfWeekString(dateObj2.getDay());	document.write(dayOfWeekStr);	document.write("</td>");	document.write("</tr>");	document.write("<tr>");	document.write('<td colspan="2" align="center">');	document.write("<table>");	for (j = 0; j < 4; j++)	{		document.write("<tr>");		document.write("<td>");		document.write(obj[i].physicianInfoArray[j].lastName);		document.write("</td>");		document.write("<td>");		document.write(obj[i].physicianInfoArray[j].shiftShortname);		document.write("</td>");		document.write("</tr>");	}	document.write("</table>");	document.write("</td>");	document.write("</tr>");	document.write("</table>");	document.write("</td>");}document.write("</tr>");}function dayOfWeekString(dayOfWeek){	var str;	switch (dayOfWeek) {		case 0:			str = "Sun";			break;		case 1:			str = "Mon";			break;		case 2:			str = "Tue";			break;		case 3:			str = "Wed";			break;		case 4:			str = "Thu";			break;		case 5:			str = "Fri";			break;		case 6:			str = "Sat";			break;	}	return(str);}