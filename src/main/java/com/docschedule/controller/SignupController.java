
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import com.docschedule.model.service.SignupUser;

public class SignupController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String cofirm = request.getParameter("confirm");
        String toEmail = request.getParameter("email");

        if (toEmail == null || toEmail.equals("")) {
            request.getSession().setAttribute("signupErrorMessage", "Email must be entered");
            request.getRequestDispatcher("signup.html").forward(request, response);
        }
        else {

            request.getSession().removeAttribute("signupErrorMessage");

            SignupUser.addNewUser(username, password, toEmail, getServletContext(), request);

            response.sendRedirect("signup_message.html");
        }
    }
}
