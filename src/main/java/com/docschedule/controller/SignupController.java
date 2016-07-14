
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.docschedule.model.service.SignupUser;
import com.docschedule.model.service.ServiceException;

public class SignupController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        String toEmail = request.getParameter("email");

        List<String> errorMessages = new ArrayList<String>();

        validateParams(errorMessages, username, password, confirm, toEmail);

        if (errorMessages.size() > 0) {
            request.setAttribute("signupErrorMessages", errorMessages);
            request.getRequestDispatcher("signup.html").forward(request, response);
        }
        else {
            boolean gotServiceException = false;
            try {
                SignupUser.addNewUser(username, password, toEmail, getServletContext(), request);
            } catch (ServiceException e) {
                gotServiceException = true;
            }

            if (gotServiceException) {
                response.sendRedirect("signup_failure.html");
            } else {
                response.sendRedirect("signup_message.html");
            }
        }
    }

    private void validateParams(List<String> errorMessages, String username, String password,
                                                            String confirm, String toEmail) {
        if (username == null || username.equals("")) {
            errorMessages.add("Username must be entered.");
        }

        if (password == null || password.equals("")) {
            errorMessages.add("Password must be entered.");
        }

        if (confirm == null || confirm.equals("")) {
            errorMessages.add("Password confirm must be entered.");
        }

        if (password != null && confirm != null) {
            if (!password.equals(confirm)) {
                errorMessages.add("Password and confirm must be match.");
            }
        }

        if (toEmail == null || toEmail.equals("")) {
            errorMessages.add("Email must be entered.");
        }
    }

}
