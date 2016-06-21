
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    public static void addUser(String username, String password, String toEmail, String token) {

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
        }

    }
}
