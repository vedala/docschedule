
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.docschedule.model.domain.DailySchedule;
import com.docschedule.model.service.ViewSchedule;

public class ViewScheduleController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        request.getRequestDispatcher("schedmain_new.html").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String startDate = request.getParameter("formdate");

        int daysToDisplay = 7;

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = sDate.plusDays(daysToDisplay);
        String endDate = eDate.toString();

        List<DailySchedule> scheduleArr = ViewSchedule.getSchedule(startDate, endDate);

        request.setAttribute("scheduleArr", scheduleArr);

        request.getRequestDispatcher("schedmain_new.html").forward(request, response);
    }
}
