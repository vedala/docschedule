var HttpClient = function() {    this.get = function(aUrl, aCallback) {        var anHttpRequest = new XMLHttpRequest();        anHttpRequest.onreadystatechange = function() {             if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)                aCallback(anHttpRequest.responseText);        }        anHttpRequest.open( "GET", aUrl, true );                    anHttpRequest.send( null );    }}var dateStr = "2015-08-06";var dateObj = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));var dayOfWeek = dateObj.getDay();if (dayOfWeek != 6) {	dateObj.setDate(dateObj.getDate() - dayOfWeek);}var startDate = dateObj.toISOString().substr(0, 10);dateObj.setDate(dateObj.getDate() + 6);var endDate = dateObj.toISOString().substr(0, 10);aClient = new HttpClient();aClient.get('http://localhost:8080/docsched/api/v1/dailyschedule?startdate=2015-08-06&enddate=2015-08-16', function(response) {var obj = JSON.parse(response);document.write('<table cellpadding="5" border="1">');for(i = 0; i < 9; i++){		document.write("<tr>");	document.write("<td>");	document.write(obj[i].scheduleDate);//	var dateStr = obj[i].scheduleDate;//	document.write(" --" + dateStr.substr(0,4) + "--" + dateStr.substr(5,2) + "--" + dateStr.substr(8,2) + "--");//	var dateObj = new Date(Date.parse(dateStr + "T00:00:00Z"));//	var dateObj = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));//	document.write(" --" + dateObj.toString());	document.write(" --" + startDate + "--" + endDate);	document.write("</td>");	document.write("<td>");	document.write("<table>");	for (j = 0; j < 4; j++)	{		document.write("<tr>");		document.write("<td>");		document.write(obj[i].physicianInfoArray[j].lastName);		document.write("</td>");		document.write("<td>");		document.write(obj[i].physicianInfoArray[j].shiftShortname);		document.write("</td>");		document.write("</tr>");	}	document.write("</table>");	document.write("</td>");	document.write("</tr>");}document.write("</table>");document.close();});