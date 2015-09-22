
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

	var month1Num = -1
	var month2Num = -1;
	var month1Str = null;
	var month2Str = null;
	var localeOptions = {month : 'short' };
	
	//
	// First column displays short month name. If a row consists of days
	// from two separate months, then month name is displayed as "Sep / Oct"
	//
	for (i = 0; i < weekSched.length; i++)
	{
		var dateStr = weekSched[i].scheduleDate;
		var dateObj2 = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));

		if (month1Num == -1)
		{
			month1Str = dateObj2.toLocaleString("en-US", localeOptions);
			month1Num = dateObj2.getMonth();
		}
		else if (month2Num == -1)
		{
			if (month1Num != dateObj2.getMonth())
			{
				month2Str = dateObj2.toLocaleString("en-US", localeOptions);
				month2Num = dateObj2.getMonth();
			}
		}
	}

	var monthStr;
	if (month2Num == -1)
	{
		monthStr = month1Str;
	}
	else
	{
		monthStr = month1Str + " / " + month2Str;
	}

	schedString += '<td class="monthcolcell">'
				+ monthStr
				+ '</td>';

	for (i = 0; i < weekSched.length; i++)
	{
		var dateStr = weekSched[i].scheduleDate;
		var dateObj2 = new Date(Date.parse(dateStr.substr(5,2) + "/" + dateStr.substr(8,2) + "/" + dateStr.substr(0,4)));

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
	var dayOfWeekWords = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
	
	return dayOfWeekWords[dayOfWeek];
}
