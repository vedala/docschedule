
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.docschedule.model.service.SignupUser;

public class ViewScheduleController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {

        ArrayList<PhysicianInfo> scheduleArr = ViewSchedule.getSchedule();

        request.getRequestDispatcher("schedmain_new.html").forward(request, response);
    }
}
