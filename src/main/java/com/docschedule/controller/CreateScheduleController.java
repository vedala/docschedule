
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.docschedule.model.service.CreateSchedule;

public class CreateScheduleController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String startDate = request.getParameter("startdate");
        String endDate   = request.getParameter("enddate");

        List<String> errorMessage = new ArrayList<String>();

        if (startDate == null || startDate.equals("")) {
            errorMessage.add("Start date must be entered.");
        }
        else {
            try {
                LocalDate.parse(startDate);
            } catch (DateTimeParseException e) {
                errorMessage.add("Start date must in format YYYY-MM-DD");
            }
        }

        if (endDate == null || endDate.equals("")) {
            errorMessage.add("End date must be entered.");
        }
        else {
            try {
                LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                errorMessage.add("End date must in format YYYY-MM-DD");
            }
        }

        if (errorMessage.size() > 0) {
            request.setAttribute("createErrorMessages", errorMessage);
            request.getRequestDispatcher("createsched.html").forward(request, response);
        }
        else {
            CreateSchedule.createSchedule(startDate, endDate);
            response.sendRedirect("create_success.html");
        }

    }
}

