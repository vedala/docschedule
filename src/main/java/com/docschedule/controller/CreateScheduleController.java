
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.docschedule.model.service.CreateSchedule;

public class CreateScheduleController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {

        String startDate = request.getParameter("startdate");
        String endDate   = request.getParameter("enddate");

        CreateSchedule.createSchedule(startDate, endDate);

        response.sendRedirect("create_success.html");

    }
}

