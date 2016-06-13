
package com.docschedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.nio.charset.StandardCharsets;

public class SignupUser extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        DataSource dataSource = null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            connection = dataSource.getConnection();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte hashedBytes[] = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = Hex.encodeHexString(hashedBytes);

            String sqlString = "insert into users (username, password) values (?, ?)";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not available", e);
        }

        try {
            String url = "/signup_message.html";
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
