/*
 * DailyScheduleResource.java
 *
 * This file contains the REST web service that fetches
 * schedule data from database and outputs the data in
 * JSON format.
 *
 */ 

package com.docschedule.services;

import com.docschedule.model.domain.DailySchedule;
import com.docschedule.model.domain.PhysicianInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Path("/v1/rangeschedule")
public class DateRangeScheduleResource {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private DataSource dataSource;

    public DateRangeScheduleResource() {
    	try {
    		Context context = new InitialContext();
    		dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
    	} catch (NamingException e) {
    		e.printStackTrace();
    	}
    }

    @GET
    @Produces("application/json")
    public String getDateRangeSchedule(@QueryParam("startdate") String startDate,
    							   @QueryParam("enddate") String endDate) {


    	ObjectMapper mapper = new ObjectMapper();
    	String sqlString;
    	ArrayList<DailySchedule> dailyScheduleArray = null;
    	ArrayList<PhysicianInfo> physicianInfoArray;
    	DailySchedule dailyScheduleItem;
    	PhysicianInfo physicianInfoItem;
    	String jsonString = null;
    	String prevSchedDate;
    	String currSchedDate;
    	try {
    		
    		connect = dataSource.getConnection();
    
    		sqlString =
    			"select date_format(a.schedule_date, '%Y-%m-%d') sched_date, "
    			+ "b.last_name PHYS_LAST, c.shortname SHIFT_SN "
    			+ "from schedule a "
    			+ "inner join physicians as b on a.physician_id = b.physician_id "
    			+ "inner join shifts as c on a.shift_id = c.shift_id "
    			+ "where a.schedule_date >= ? "
    			+ "and a.schedule_date <= ? "
    			+ "order by a.schedule_date, b.last_name, a.shift_id";

    		preparedStatement = connect.prepareStatement(sqlString);
    		preparedStatement.setString(1, startDate);
    		preparedStatement.setString(2, endDate);
    		resultSet = preparedStatement.executeQuery();
    		
    		dailyScheduleArray = new ArrayList<DailySchedule>();
    		physicianInfoArray = new ArrayList<PhysicianInfo>();
    		prevSchedDate = null;

    		while (resultSet.next()) {
    			currSchedDate = resultSet.getString("SCHED_DATE");
    			if (prevSchedDate != null && !currSchedDate.equals(prevSchedDate)) {
    				dailyScheduleItem = new DailySchedule();
    				dailyScheduleItem.setScheduleDate(prevSchedDate);
    				dailyScheduleItem.setPhysicianInfoArray(physicianInfoArray);
    				dailyScheduleArray.add(dailyScheduleItem);
    				physicianInfoArray = new ArrayList<PhysicianInfo>();
    			}
    			physicianInfoItem = new PhysicianInfo();
    			physicianInfoItem.setLastName(resultSet.getString("PHYS_LAST"));
    			physicianInfoItem.setShiftShortname(resultSet.getString("SHIFT_SN"));
    			physicianInfoArray.add(physicianInfoItem);
    			prevSchedDate = currSchedDate;
    		}

    		if (physicianInfoArray.size() > 0) {
    			dailyScheduleItem = new DailySchedule();
    			dailyScheduleItem.setScheduleDate(prevSchedDate);
    			dailyScheduleItem.setPhysicianInfoArray(physicianInfoArray);
    			dailyScheduleArray.add(dailyScheduleItem);
    		}

    	}
    	catch (SQLException e) {
    		System.out.println("SQL Exception");
    		System.out.println("SQLException: " + e.getMessage());
    		System.out.println("SQLState: " + e.getSQLState());
    		System.out.println("VendorError: " + e.getErrorCode());
    	} finally {
    		try {
    			connect.close();
    		} catch (SQLException e) {
    			System.out.println("SQL Exception-close");
    			System.out.println("SQLException: " + e.getMessage());

    		}
    	}

    	try {
    		jsonString = mapper.writeValueAsString(dailyScheduleArray);
    	}
    	catch (JsonProcessingException e) {
    		System.out.println("JSON Processing Exception");
    	}

    	return (jsonString);
    }

}
