
//
// Number of weeks to display
//

var weeksToDisplay = 6;


// Get today's date, then extract today's day-of-week.
// Based on today's day-of-week, calculate date of
// most recent Sunday prior to today. This is required
// since schedule is displayed Sunday thru Saturday for
// each week.

var dateObj = new Date();
var dayOfWeek = dateObj.getDay();
if (dayOfWeek != 0) {
	dateObj.setDate(dateObj.getDate() - dayOfWeek);
}

var startDates = [];
var endDates = [];
populateStartEndDates(startDates, endDates, dateObj);

// schedHtml holds the main HTML that will display the schedule.
// displayWeek is an array that holds json fetched from server
// for each week.


var schedHtml;
var displayWeek = [];

schedHtml = '<table class="outermosttab">';
schedHtml += displayTableHeading();

var myUrl = constructURL(startDates[0], endDates[0]);

$.ajax({
		url		: myUrl,
		type	: 'GET',
		success	: function(response) {
			schedHtml += '<tr>';
			displayWeek[0] = weeklySchedToHtml(response);
			schedHtml += displayWeek[0];
			schedHtml += '</tr>';
			myUrl = constructURL(startDates[1], endDates[1]);
			$.ajax({
					url		: myUrl,
					type	: 'GET',
					success	: function(response) {
						schedHtml += '<tr>';
						displayWeek.push(weeklySchedToHtml(response));
						schedHtml += displayWeek[1];
						schedHtml += '</tr>';
						myUrl = constructURL(startDates[2], endDates[2]);
						$.ajax({
							url		: myUrl,
							type	: 'GET',
							success	: function(response) {
								schedHtml += '<tr>';
								displayWeek.push(weeklySchedToHtml(response));
								schedHtml += displayWeek[2];
								schedHtml += '</tr>'
								myUrl = constructURL(startDates[3], endDates[3]);
								$.ajax({
									url		: myUrl,
									type	: 'GET',
									success	: function(response) {
										schedHtml += '<tr>';
										displayWeek.push(weeklySchedToHtml(response));
										schedHtml += displayWeek[3];
										schedHtml += '</tr>';
										myUrl = constructURL(startDates[4], endDates[4]);
										$.ajax({
											url		: myUrl,
											type	: 'GET',
											success	: function(response) {
												schedHtml += '<tr>';
												displayWeek.push(weeklySchedToHtml(response));
												schedHtml += displayWeek[4];
												schedHtml += '</tr>';
												myUrl = constructURL(startDates[5], endDates[5]);
												$.ajax({
													url		: myUrl,
													type	: 'GET',
													success	: function(response) {
														schedHtml += '<tr>';
														displayWeek.push(weeklySchedToHtml(response));
														schedHtml += displayWeek[5];
														schedHtml += '</tr>'
																	+ '</table>';
														$("#maintable").html(schedHtml);
														document.close();
													}
												});
											}
										});
									}
								});
							}
						});
					}
			});
		}
});


function weeklySchedToHtml(weekSched)
{
	var schedString = '';
	var i, j;
	var monthNum1;
	var monthNum2;

	monthNum1 = -1;
	monthNum2 = -1;
	for (i = 0; i < weekSched.length; i++)
	{
		var dateStr = weekSched[i].scheduleDate;
		var dateObj2 = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));

		if (monthNum1 == -1)
		{
			monthNum1 = dateObj2.getMonth();
		}
		else if (monthNum1 != dateObj2.getMonth())
		{
			monthNum2 = dateObj2.getMonth();
		}
	}

	var monthStr;
	if (monthNum2 == -1)
	{
		monthStr = monthToString(monthNum1);
	}
	else
	{
		monthStr = monthToString(monthNum1) + " / " + monthToString(monthNum2);
	}

	schedString += '<td class="monthcolcell">'
				+ monthStr
				+ '</td>';

	for (i = 0; i < weekSched.length; i++)
	{
		var dateStr = weekSched[i].scheduleDate;
		var dateObj2 = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));
		var dayOfWeekStr = dayOfWeekString(dateObj2.getDay());


		schedString += '<td class="onedaycell">'
				+ '<table class="datedaytab">'
				+ '<tr>'
				+ '<td class="dateonly">'
				+ dateStr.substr(8,2)
				+ '</td>'
				+ '</tr>'
				+ '</table>'
				+ '<table class="doclisttab">';
				
		for (j = 0; j < weekSched[i].physicianInfoArray.length; j++)
		{
			schedString += '<tr>'
						+ '<td>'
						+ weekSched[i].physicianInfoArray[j].lastName
						+ '</td>'
						+ '<td>'
						+ weekSched[i].physicianInfoArray[j].shiftShortname
						+ '</td>'
						+ '</tr>';
		}
		
		schedString += '</table>'
						+ '</td>';
	
	}

	return(schedString);
}

function constructURL(startDate, endDate)
{
	return ("http://www.coderapids.com/docsched/api/v1/dailyschedule?startdate="
			+ startDate + "&enddate=" + endDate);
}


function displayTableHeading()
{
	var htmlString;

	htmlString = '<tr id="toprow">';
	htmlString += '<td>'
				+ '</td>';

	for (i = 0; i < 7; i++) {
		htmlString += '<th>'
					+ dayOfWeekString(i)
					+ '</th>';
	}
	htmlString += '</tr>';
	return htmlString;
}


function populateStartEndDates(startDates, endDates, dateObj)
{
	var i;
	for (i = 0; i < weeksToDisplay; i++)
	{
		startDates.push(dateObj.toISOString().substr(0, 10));
		dateObj.setDate(dateObj.getDate() + 6);
		endDates.push(dateObj.toISOString().substr(0, 10));
		dateObj.setDate(dateObj.getDate() + 1);
	}
}


function dayOfWeekString(dayOfWeek)
{
	var str;
	switch (dayOfWeek) {
		case 0:
			str = "Sun";
			break;
		case 1:
			str = "Mon";
			break;
		case 2:
			str = "Tue";
			break;
		case 3:
			str = "Wed";
			break;
		case 4:
			str = "Thu";
			break;
		case 5:
			str = "Fri";
			break;
		case 6:
			str = "Sat";
			break;
		default:
			str = "Err";
			break;
	}
	return(str);
}

function monthToString(monthNum)
{
	var str;
	switch (monthNum) {
		case 0:
			str = "Jan";
			break;
		case 1:
			str = "Feb";
			break;
		case 2:
			str = "Mar";
			break;
		case 3:
			str = "Apr";
			break;
		case 4:
			str = "May";
			break;
		case 5:
			str = "Jun";
			break;
		case 6:
			str = "Jul";
			break;
		case 7:
			str = "Aug";
			break;
		case 8:
			str = "Sep";
			break;
		case 9:
			str = "Oct";
			break;
		case 10:
			str = "Nov";
			break;
		case 11:
			str = "Dev";
			break;
		default:
			str = "Err";
	}
	return(str);
}
