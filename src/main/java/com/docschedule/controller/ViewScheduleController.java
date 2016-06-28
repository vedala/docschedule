
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        HttpSession session = request.getSession();

        // if startDate attribute exists in session, then display results by
        // calling doPost(), otherwise just navigate to display page
    
        String startDate = (String) session.getAttribute("startDate");
        request.setAttribute("fromGet", "Y");

        if (startDate == null) {
            request.getRequestDispatcher("schedmain_new.html").forward(request, response);
        }
        else {
            doPost(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String startDate = null;
        HttpSession session = request.getSession();

        String isFromGet = (String) request.getAttribute("fromGet");

        if (isFromGet == null) {
            startDate = request.getParameter("formdate");
            session.setAttribute("startDate", startDate);
        }
        else {
            startDate = (String) session.getAttribute("startDate");
        }

        int daysToDisplay = 10;

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = sDate.plusDays(daysToDisplay-1);
        String endDate = eDate.toString();

        List<DailySchedule> scheduleArr = ViewSchedule.getSchedule(startDate, endDate);

        request.setAttribute("scheduleArr", scheduleArr);

        request.getRequestDispatcher("schedmain_new.html").forward(request, response);
    }
}
