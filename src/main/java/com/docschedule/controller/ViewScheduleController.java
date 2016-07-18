
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.docschedule.model.domain.DailySchedule;
import com.docschedule.model.dao.DailyScheduleDAO;
import com.docschedule.model.dao.DAOException;

public class ViewScheduleController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        HttpSession session = request.getSession();

        // if startDate attribute exists in session, then display results by
        // calling doPost(), otherwise just navigate to display page
    
        String startDate = (String) session.getAttribute("startDate");
        request.setAttribute("fromGet", "Y");

        if (startDate == null) {
            request.getRequestDispatcher("schedmain.html").forward(request, response);
        }
        else {
            doPost(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        int daysToDisplay = 7;
        String startDate = null;
        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        String isFromGet = (String) request.getAttribute("fromGet");

        if (isFromGet != null) {
            // Came to this method via GET
            startDate = (String) session.getAttribute("startDate");
        }
        else {
            // Came to this method via POST
            String upButton = (String) request.getParameter("up");
            String downButton = (String) request.getParameter("down");

            // If up and down buttons not clicked, implies Go button clicked
            if ((upButton != null) || (downButton != null)) {
                startDate = (String) session.getAttribute("startDate");
                LocalDate tempDate = LocalDate.parse(startDate);
                if (upButton != null) {
                    tempDate = tempDate.minusDays(1);
                }
                else {
                    tempDate = tempDate.plusDays(1);
                }
                startDate = tempDate.toString();
            }
            else {
                startDate = request.getParameter("formdate");
            }

            // Validate date entered in form field, if valid save in session

            validateParams(errorMessages, startDate, upButton, downButton);
            if (errorMessages.size() == 0) {
                session.setAttribute("startDate", startDate);
            }
        }

        boolean gotDAOException = false;

        if (errorMessages.size() == 0 && startDate != null) {
            LocalDate sDate = LocalDate.parse(startDate);
            LocalDate eDate = sDate.plusDays(daysToDisplay-1);
            String endDate = eDate.toString();

            DailyScheduleDAO dailyScheduleDAO = new DailyScheduleDAO();
            try {
                List<DailySchedule> scheduleArr =
                            dailyScheduleDAO.getScheduleByDateRange(startDate, endDate);
                request.setAttribute("scheduleArr", scheduleArr);
            } catch (DAOException e) {
                gotDAOException = true;
            }
        }
        else {
            request.setAttribute("dispSchedErrorMessages", errorMessages);
        }

        if (gotDAOException) {
            request.getRequestDispatcher("dispsched_fail.html").forward(request, response);
        } else {
            request.getRequestDispatcher("schedmain.html").forward(request, response);
        }
    }

    private void validateParams(List<String> errorMessages, String startDate,
                                                    String upButton, String downButton) {

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
    }
}
