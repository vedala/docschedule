
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SideDAO {

    public Date getStartDate(int sideId) throws DAOException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.SideDAO");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date startDate = null;
        DataSource ds = null;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            throw new DAOException("NamingException encountered", e);
        }

        try {
            connection = ds.getConnection();

            String sqlString =   "select start_date from sides where side_id = ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, sideId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            startDate = resultSet.getDate(1);

        } catch (SQLException e) {
            logger.error("getStartDate - data access", e);
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("getStartDate - connection close", e);
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }

        return startDate;

    }

}
