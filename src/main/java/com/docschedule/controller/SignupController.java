
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.docschedule.model.service.SignupUser;

public class SignupController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String toEmail = request.getParameter("email");

        SignupUser.addNewUser(username, password, toEmail, getServletContext(), request);

        response.sendRedirect("signup_message.html");
    }
}
