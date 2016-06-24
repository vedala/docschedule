
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;


public class ScheduleDAO {

    public static void addSchedule(Date currDate, int physicianId, int shiftId) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;

        try {
            connection = ds.getConnection();

            String sqlString = "insert into schedule(schedule_date, physician_id, shift_id) "
                               + "values (?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setDate(1, currDate);
            preparedStatement.setInt(2, physicianId);
            preparedStatement.setInt(3, shiftId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
            }
        }
    }
}
