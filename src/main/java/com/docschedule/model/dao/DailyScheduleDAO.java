
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;

import com.docschedule.model.domain.DailySchedule;
import com.docschedule.model.domain.PhysicianInfo;

public class DailyScheduleDAO {

    public List<DailySchedule> getScheduleByDateRange(String startDate, String endDate) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

    	ArrayList<DailySchedule> dailyScheduleArray = null;
    	ArrayList<PhysicianInfo> physicianInfoArray;
    	DailySchedule dailyScheduleItem;
    	PhysicianInfo physicianInfoItem;
    	String prevSchedDate;
    	String currSchedDate;
    	try {
    		
    		connection = ds.getConnection();
    
    		String sqlString =
    			"select date_format(a.schedule_date, '%Y-%m-%d') sched_date, "
    			+ "b.last_name PHYS_LAST, b.first_name PHYS_FIRST, c.shortname SHIFT_SN "
    			+ "from schedule a "
    			+ "inner join physicians as b on a.physician_id = b.physician_id "
    			+ "inner join shifts as c on a.shift_id = c.shift_id "
    			+ "where a.schedule_date >= ? "
    			+ "and a.schedule_date <= ? "
    			+ "order by a.schedule_date, b.last_name, b.first_name, a.shift_id";

    		preparedStatement = connection.prepareStatement(sqlString);
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
    			physicianInfoItem.setFirstName(resultSet.getString("PHYS_FIRST"));
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
    			connection.close();
    		} catch (SQLException e) {
    			System.out.println("SQL Exception-close");
    			System.out.println("SQLException: " + e.getMessage());

    		}
    	}

        return dailyScheduleArray;
    }
}
