
package com.docschedule.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.docschedule.model.dao.UserDAO;

public class VerifyEmailController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException, ServletException {

        String path = request.getPathInfo();
        String[] splitPath = path.split("/");
        String verifyToken = splitPath[1];

        UserDAO userDAO = new UserDAO();
        userDAO.updateUserVerified(verifyToken);

        StringBuilder redirectString = new StringBuilder();
        String contextPath = request.getContextPath();
        if (!contextPath.equals("")) {
            redirectString.append(contextPath);
        }
        redirectString.append("/email_verified.html");
        response.sendRedirect(redirectString.toString());

    }
}

