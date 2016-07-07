
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    public void addUser(String username, String password, String toEmail, String token) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;

        try {
            connection = ds.getConnection();

            String sqlString =   "insert into users (username, password, email, token) "
                               + "values (?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, toEmail);
            preparedStatement.setString(4, token);
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

    public void updateUserVerified(String token) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;

        try {
            connection = ds.getConnection();

            String sqlString =   "update users set verified = 1 where token = ? and verified = 0";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, token);
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
