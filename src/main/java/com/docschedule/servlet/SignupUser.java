
package com.docschedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.InputStream;

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
import java.util.UUID;
import java.util.Properties;

public class SignupUser extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                                    throws IOException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        DataSource dataSource = null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String toEmail = request.getParameter("email");

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

        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString();

        System.out.println("uuid string="+uuidString);
        System.out.println("getrequest uri = " + request.getRequestURI());
        System.out.println("getrequest url = " + request.getRequestURL());
        System.out.println("getservlet path = " + request.getServletPath());

        // read properties file
        // get host, port, userEmail, refreshToken

        //String file = "/WEB-INF/email.properties";
        // ServletContext servContext = getServletContext();

        String path = getServletContext().getRealPath("/WEB-INF/classes");
        Properties properties = new Properties();
        try {
            String file = path + "/" + "email.properties";
System.out.println("file-name="+file);
            InputStream is = new FileInputStream(file);
            properties.load(is);
            System.out.println(properties.getProperty("host"));
            System.out.println(properties.getProperty("port"));
            System.out.println(properties.getProperty("userEmail"));
            System.out.println(properties.getProperty("refreshToken"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // sendMessage
/*
        SendMessage sm = new SendMessage();
        String message = "Click on the link below to activate your account:\n\n"
                         + 
        sm.sendMessage(host, port, userEmail, toEmail, oauthToken, username, message)
*/

        response.sendRedirect("signup_message.html");

    }
}
