
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

    public void addSchedule(Date currDate, int physicianId, int shiftId) throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered");
        }

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
            throw new DAOException("SQLException during data access");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQLException on attempt to close connection");
            }
        }
    }

    public int getScheduleCount() throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = -1;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered");
        }

        try {
            connection = ds.getConnection();

            String sqlString =   "select count(*) from schedule";

            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("SQLException during data access");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQLException on attempt to close connection");
            }
        }

        return count;
    }

    public int checkScheduleInRange(String startDate, String endDate) throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = -1;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered");
        }

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
            throw new DAOException("SQLException during data access");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQLException on attempt to close connection");
            }
        }

        return count;
    }

    public Date getMaxScheduleDate() throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date maxDate = null;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered");
        }

        try {
            connection = ds.getConnection();

            String sqlString =   "select max(schedule_date) from schedule";

            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            maxDate = resultSet.getDate(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("SQLException during data access");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQLException on attempt to close connection");
            }
        }

        return maxDate;

    }
}
