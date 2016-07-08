
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


public class ScheduleDAO {

    public void addSchedule(Date currDate, int physicianId, int shiftId) {

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

    public int getScheduleCount() {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = -1;

        try {
            connection = ds.getConnection();

            String sqlString =   "select count(*) from schedule";

            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

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

        return count;
    }

    public int checkScheduleInRange(String startDate, String endDate) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = -1;

        try {
            connection = ds.getConnection();

            String sqlString =   "select count(*) from schedule where "
                                            + "schedule_date between ? and ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

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

        return count;
    }

    public Date getMaxScheduleDate() {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date maxDate = null;

        try {
            connection = ds.getConnection();

            String sqlString =   "select max(schedule_date) from schedule";

            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            maxDate = resultSet.getDate(1);

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

        return maxDate;

    }
}
