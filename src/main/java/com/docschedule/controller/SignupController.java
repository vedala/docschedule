
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.docschedule.model.service.SignupUser;

public class SignupController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        String toEmail = request.getParameter("email");

        List<String> errorMessage = new ArrayList<String>();

        if (username == null || username.equals("")) {
            errorMessage.add("Username must be entered.");
        }

        if (password == null || password.equals("")) {
            errorMessage.add("Password must be entered.");
        }

        if (confirm == null || confirm.equals("")) {
            errorMessage.add("Password confirm must be entered.");
        }

        if (password != null && confirm != null) {
            if (!password.equals(confirm)) {
                errorMessage.add("Password and confirm must be match.");
            }
        }

        if (toEmail == null || toEmail.equals("")) {
            errorMessage.add("Email must be entered.");
        }

        if (errorMessage.size() > 0) {
            request.setAttribute("signupErrorMessages", errorMessage);
            request.getRequestDispatcher("signup.html").forward(request, response);
        }
        else {
            SignupUser.addNewUser(username, password, toEmail, getServletContext(), request);
            response.sendRedirect("signup_message.html");
        }
    }
}
