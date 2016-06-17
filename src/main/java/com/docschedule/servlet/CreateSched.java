
package com.docschedule.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CreateSched extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        DataSource dataSource = null;
        ResultSet resultSet = null;

        // Following code suitable for creating schedule the first time
        // additional checks are needed if data already exists

        // fetch schedule start date and end dates
        String startDateStr = request.getParameter("startdate");
        String endDateStr   = request.getParameter("enddate");
        System.out.println("request-startdate="+startDateStr);
        System.out.println("request-enddate="+endDateStr);

        // Fetch side 1 start date
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        Date startDateSide1 = null;

        try {
            connection = dataSource.getConnection();

            String sqlString = "select start_date from sides where side_id = 1";
            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            startDateSide1 = resultSet.getDate(1);
            System.out.println("startDateSide1="+startDateSide1.toString());
        } catch (SQLException e) {
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

        // determine if schedule start date should start at side 1 or side 2

        Date startDate = Date.valueOf(startDateStr);

        int daysBetween = ((int) (startDate.getTime() / (24*60*60*1000)) ) 
                            - ((int) (startDateSide1.getTime() / (24*60*60*1000)));
        System.out.println("daysbetween="+daysBetween);

        int side = -1;
        if ((daysBetween % 14) < 7) {
            side = 1;
        }
        else {
            side = 2;
        }

        System.out.println("side="+side);

        // create schedule records keeping in mind night order
    }
}
