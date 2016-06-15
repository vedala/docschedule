
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

import com.docschedule.util.SendMessage;

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

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        try {
            connection = dataSource.getConnection();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte hashedBytes[] = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = Hex.encodeHexString(hashedBytes);

            String sqlString =   "insert into users (username, password, email, token) "
                               + "values (?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, toEmail);
            preparedStatement.setString(4, uuidString);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not available", e);
        }

        // Read properties file

        Properties properties = new Properties();

        InputStream inputStream = null;
        String host = null;
        int port = -1;
        String userEmail = null;
        String refreshToken = null;
        String clientId = null;
        String clientSecret = null;

        try {
            String file = "/WEB-INF/classes/email.properties";
            inputStream =
                 getServletContext().getResourceAsStream("/WEB-INF/classes/email.properties");
            properties.load(inputStream);
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            userEmail = properties.getProperty("userEmail");
            refreshToken = properties.getProperty("refreshToken");
            clientId = properties.getProperty("clientId");
            clientSecret = properties.getProperty("clientSecret");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }


        // Send message

        SendMessage sm = new SendMessage();
        StringBuilder verifyURL = new StringBuilder();
        verifyURL.append(request.getScheme()).append("://").append(request.getServerName());

        int serverPort = request.getServerPort();
        if (serverPort != 80 && serverPort != 443) {
            verifyURL.append(":").append(serverPort);
        }

        String contextPath = request.getContextPath();
        if (!contextPath.equals("")) {
            verifyURL.append(contextPath);
        }

        verifyURL.append("/api/v1/").append("VerifyEmail").append("/").append(uuidString);
        String message = "Click on the link below to activate your account:\n\n"
                         + verifyURL.toString();
        sm.sendMessage(host, port, userEmail, toEmail, refreshToken,
                                                clientId, clientSecret, username, message);

        response.sendRedirect("signup_message.html");

    }
}
