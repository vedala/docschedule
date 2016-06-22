
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


public class SideDAO {

    public static Date getStartDate(int sideId) {

        Connection connection = null;
        DataSource ds = AppDataSource.getDataSource();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date startDate = null;

        try {
            connection = ds.getConnection();

            String sqlString =   "select start_date from sides where side_id = ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, sideId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            startDate = resultSet.getDate(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return startDate;

    }

}
