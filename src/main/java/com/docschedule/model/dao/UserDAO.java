
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

    public void addUser(String username, String password, String toEmail, String token)
                                                        throws DAOException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.UserDAO");
        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            throw new DAOException("NamingException encountered", e);
        }

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
            logger.error("addUser - data access", e);
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("addUser - connection close", e);
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }

    }

    public void updateUserVerified(String token) throws DAOException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.UserDAO");
        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered", e);
        }

        try {
            connection = ds.getConnection();

            String sqlString =   "update users set verified = 1 where token = ? and verified = 0";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, token);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("addUser - data access", e);
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("addUser - connection close", e);
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }
    }

    public boolean checkUserExists(String username) throws DAOException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.UserDAO");
        Connection connection = null;
        DataSource ds = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = -1;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered", e);
        }

        try {
            connection = ds.getConnection();

            String sqlString =   "select count(*) from users where username = ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

        } catch (SQLException e) {
            logger.error("checkUserExists - data access", e);
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("checkUserExists - connection close", e);
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }

        return (count > 0) ? true : false;
    }
}
