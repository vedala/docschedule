package com.coderapids.docsched.services;

import com.coderapids.docsched.domain.DailySchedule;
import com.coderapids.docsched.domain.PhysicianInfo;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.NodeList;

// import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.StreamingOutput;
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// import org.json.simple.JSONObject;
// import org.json.simple.JSONArray;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/v1/dailyschedule")
public class DailyScheduleResource {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DailyScheduleResource() {
	}

	@GET
	@Produces("application/json")
	public String getDailySchedule(@QueryParam("startdate") String startDate,
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
			Class.forName("com.mysql.jdbc.Driver");
			
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/docsched?"
									+ "user=docdbowner&password=docdbpwd");
	
			sqlString =
				"select date_format(a.schedule_date, '%Y-%m-%d') sched_date, "
				+ "b.last_name PHYS_LAST, c.shortname SHIFT_SN "
				+ "from schedule_physicians a "
				+ "inner join physicians as b on a.physician_id = b.physician_id "
				+ "inner join shifts as c on a.shift_id = c.shift_id "
				+ "where a.schedule_date >= '" + startDate
				+ "' and a.schedule_date <= '" + endDate + "' "
				+ "order by a.schedule_date, b.last_name, a.shift_id";
System.out.println("sqlString=["+sqlString+"]");
			preparedStatement = connect.prepareStatement(sqlString);
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
		catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} catch (SQLException e) {
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

		System.out.println(jsonString);
		return (jsonString);
	}
	
	public void updateDailySchedule() {
	}
	
}