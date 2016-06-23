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

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Path("/v1/onedayschedule")
public class OneDayScheduleResource {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private DataSource dataSource;

	public OneDayScheduleResource() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces("application/json")
	public String getOneDaySchedule(@QueryParam("date") String date) {

		ObjectMapper mapper = new ObjectMapper();
		String sqlString;
		ArrayList<PhysicianInfo> physicianInfoArray = null;
		DailySchedule dailyScheduleItem = null;
		PhysicianInfo physicianInfoItem = null;
		String jsonString = null;
		try {
			
			connect = dataSource.getConnection();
	
			sqlString =
				"select b.last_name PHYS_LAST, c.shortname SHIFT_SN "
				+ "from schedule a "
				+ "inner join physicians as b on a.physician_id = b.physician_id "
				+ "inner join shifts as c on a.shift_id = c.shift_id "
				+ "where a.schedule_date = ? "
				+ "order by a.schedule_date, b.last_name, a.shift_id";

			preparedStatement = connect.prepareStatement(sqlString);
			preparedStatement.setString(1, date);
			resultSet = preparedStatement.executeQuery();
			
			physicianInfoArray = new ArrayList<PhysicianInfo>();
			dailyScheduleItem = new DailySchedule();
			dailyScheduleItem.setScheduleDate(date);
			dailyScheduleItem.setPhysicianInfoArray(physicianInfoArray);

			while (resultSet.next()) {
				physicianInfoItem = new PhysicianInfo();
				physicianInfoItem.setLastName(resultSet.getString("PHYS_LAST"));
				physicianInfoItem.setShiftShortname(resultSet.getString("SHIFT_SN"));
				physicianInfoArray.add(physicianInfoItem);
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
			jsonString = mapper.writeValueAsString(dailyScheduleItem);
		}
		catch (JsonProcessingException e) {
			System.out.println("JSON Processing Exception");
		}

		return (jsonString);
	}

}
