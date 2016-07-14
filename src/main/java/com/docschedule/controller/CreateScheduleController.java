
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;

import com.docschedule.model.service.CreateSchedule;
import com.docschedule.model.service.ServiceException;
import com.docschedule.model.dao.ScheduleDAO;
import com.docschedule.model.dao.DAOException;

public class CreateScheduleController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String startDate = request.getParameter("startdate");
        String endDate   = request.getParameter("enddate");

        List<String> errorMessages = new ArrayList<String>();

        boolean validateDAOException = false;
        try {
            validateParams(errorMessages, startDate, endDate);
        } catch (DAOException e) {
            validateDAOException = true;
        }

        if (validateDAOException) {
            response.sendRedirect("create_failure.html");
        }
        else if (errorMessages.size() > 0) {
            request.setAttribute("createErrorMessages", errorMessages);
            request.getRequestDispatcher("createsched.html").forward(request, response);
        }
        else {
            boolean gotServiceException = false;
            try {
                CreateSchedule.createSchedule(startDate, endDate);
            } catch (ServiceException e) {
                gotServiceException = true;
            }
            if (gotServiceException) {
                response.sendRedirect("create_failure.html");
            } else {
                response.sendRedirect("create_success.html");
            }
        }

    }

    private void validateParams(List<String> errorMessages, String startDate, String endDate) {

        if (startDate == null || startDate.equals("")) {
            errorMessages.add("Start date must be entered.");
        }
        else {
            try {
                LocalDate.parse(startDate);
            } catch (DateTimeParseException e) {
                errorMessages.add("Start date must in format YYYY-MM-DD");
            }
        }

        if (endDate == null || endDate.equals("")) {
            errorMessages.add("End date must be entered.");
        }
        else {
            try {
                LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                errorMessages.add("End date must in format YYYY-MM-DD");
            }
        }

        // Display errors to user if any of the above validations fail

        if (errorMessages.size() > 0) {
            return;
        }

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        if (!eDate.isAfter(sDate)) {
            errorMessages.add("End date must be higher than start date");
            return;
        }

        // check if schedule already exists, if exists then perform additional checks
        // otherwise any range of dates is allowed

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        int count = scheduleDAO.getScheduleCount();
        if (count > 0) {
            // check if schedule already exists for date range specified,
            // display highest date for which schedule is available

            int rangeCount = scheduleDAO.checkScheduleInRange(startDate, endDate);
            if (rangeCount > 0) {
                Date maxDate = scheduleDAO.getMaxScheduleDate();
                String err = "Schedule exists for all or part of range provided";
                if (maxDate != null) {
                    err = err + " (schedule exists till " + maxDate.toString() + ")";
                }
                errorMessages.add(err);
            }
        }

        if (sDate.getDayOfWeek() != DayOfWeek.TUESDAY) {
            errorMessages.add("Start date must be a Tuesday");
        }

        if (eDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            errorMessages.add("End date must be a Monday");
        }
    }
}

