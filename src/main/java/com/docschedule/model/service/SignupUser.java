
package com.docschedule.model.service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.Properties;

import java.io.InputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

import com.docschedule.model.util.SendMessage;
import com.docschedule.model.dao.UserDAO;
import com.docschedule.model.dao.DAOException;
import com.docschedule.model.util.UtilException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SignupUser {

    public static void addNewUser(String username, String password, String toEmail,
                              ServletContext servletContext, HttpServletRequest request)
                                                                    throws DAOException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.SideDAO");
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String passwordHash = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte hashedBytes[] = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            passwordHash = Hex.encodeHexString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new ServiceException("SHA-256 is not available", e);
        }

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.addUser(username, passwordHash, toEmail, uuidString);
        } catch (DAOException e) {
            logger.error("DAOException in addNewUser", e);
            throw new ServiceException("DAOException encountered on userDAO.addUser", e);
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
                 servletContext.getResourceAsStream("/WEB-INF/classes/email.properties");
            properties.load(inputStream);
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            userEmail = properties.getProperty("userEmail");
            refreshToken = properties.getProperty("refreshToken");
            clientId = properties.getProperty("clientId");
            clientSecret = properties.getProperty("clientSecret");
        } catch (IOException e) {
            logger.error("addNewUser - IOException email.properties", e);
            throw new ServiceException("IOException when reading email.properties", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("getStartDate - IOException stream close", e);
                throw new ServiceException("IOException input stream close attempt", e);
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

        verifyURL.append("/VerifyEmail").append("/").append(uuidString);
        String message = "Click on the link below to activate your account:\n\n"
                         + verifyURL.toString();
        try {
            sm.sendMessage(host, port, userEmail, toEmail, refreshToken,
                                                clientId, clientSecret, username, message);
        } catch (UtilException e) {
            logger.error("addNewUser - UtilException in sendMessage", e);
            throw new ServiceException("UtilException in sendMessage", e);
        }
    }
}
