
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

import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CreateSchedule extends HttpServlet {

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
        int numPhysiciansPerSide = -1;

        try {
            connection = dataSource.getConnection();

            // Fetch side-1 start date
            String sqlString = "select start_date from sides where side_id = 1";
            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            startDateSide1 = resultSet.getDate(1);
            System.out.println("startDateSide1="+startDateSide1.toString());

            // Fetch number of physicians per side
            sqlString = "select count(1) from physicians where side_id = 1";
            preparedStatement = connection.prepareStatement(sqlString);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            numPhysiciansPerSide = resultSet.getInt(1);
            System.out.println("numphys-side="+numPhysiciansPerSide);
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
        Date endDate = Date.valueOf(endDateStr);

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
        int nightOrder = 1;
        int currSide = 1;
        int week = 1;
        Date currDate = startDate;
        int dayCount = 1;

        try {
            connection = dataSource.getConnection();

            String selectString = "select physician_id, night_order from physicians where side_id = ?";
            String insertString = "insert into schedule_physicians(schedule_date, physician_id, shift_id) "
                                  + "values (?, ?, ?)";
            PreparedStatement stmtSelectPhys = connection.prepareStatement(selectString);
            PreparedStatement stmtInsertSchedPhys = connection.prepareStatement(insertString);

            Boolean dateInRange = currDate.compareTo(endDate) <= 0 ? true : false;
            while (dateInRange) {
                stmtSelectPhys.setInt(1, currSide);
                resultSet = stmtSelectPhys.executeQuery();
                while (resultSet.next()) {
                    int physicianId = resultSet.getInt(1);
                    int nightOrderSelected = resultSet.getInt(2);
                    System.out.println("physicianId="+physicianId+ ", nightOrderSel="+nightOrderSelected);
                    int shiftIdToInsert = 1;
                    if (nightOrderSelected == nightOrder) {
                        shiftIdToInsert = 2;
                    }
                    stmtInsertSchedPhys.setDate(1, currDate);
                    stmtInsertSchedPhys.setInt(2, physicianId);
                    stmtInsertSchedPhys.setInt(3, shiftIdToInsert);
                    stmtInsertSchedPhys.executeUpdate();
                }

                // increment by 1 day
                currDate.setTime(currDate.getTime() + (24*60*60*1000));
                dateInRange = currDate.compareTo(endDate) <= 0 ? true : false;

                // get day of week
                Calendar cal = Calendar.getInstance();
                cal.setTime(currDate);
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

                dayCount++;

                // Tuesday is start of new week
                if (dayOfWeek == Calendar.TUESDAY && dayCount > 1) {
                    week++;
                    currSide = (currSide == 1) ? 2 : 1;

                    // Night order changes once every two weeks
                    if ((week > 1) && (week % 2 == 1)) {
                        nightOrder++;
                        if (nightOrder > numPhysiciansPerSide) {
                            nightOrder = 1;
                        }
                    }
                }

System.out.println("daycount="+dayCount+", week="+week+", night="+nightOrder+", currSide="+currSide);
            }
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
    }
}
