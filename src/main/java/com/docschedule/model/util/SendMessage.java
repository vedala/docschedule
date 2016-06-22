package com.docschedule.model.util;

import java.io.IOException;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

import java.security.Provider;
import java.security.Security;

import com.sun.mail.smtp.SMTPTransport;

import com.docschedule.model.util.AccessTokenFromRefreshToken;
import com.docschedule.model.util.OAuth2SaslClientFactory;

public class SendMessage {

    private static final Logger logger =
        Logger.getLogger(SendMessage.class.getName());
    
    public static final class OAuth2Provider extends Provider {
        private static final long serialVersionUIS = 1L;

        public OAuth2Provider() {
            super("Google OAuth2 Provider", 1.0,
                  "Provides the XOAUTH2 SASL Mechanism");
            put("SaslClientFactory.XOAUTH2",
                    "com.docschedule.model.util.OAuth2SaslClientFactory");
        }
    }

    public static void initialize() {
        Security.addProvider(new OAuth2Provider());
    }

    public static SMTPTransport connectToSmtp(Session session,
                                            String host,
                                            int port,
                                            String userEmail,
                                            String oauthToken,
                                            boolean debug) throws Exception {

        final URLName unusedUrlName = null;
        SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
        // If the password is non-null, SMTP tries to do AUTH LOGIN.
        final String emptyPassword = "";
        transport.connect(host, port, userEmail, emptyPassword);

        return transport;
    }

    public void sendMessage(String host, int port, String userEmail, String toEmail,
                            String refreshToken, String clientId, String clientSecret,
                            String name, String body)
    {

        try {

            initialize();

            String oauthToken = AccessTokenFromRefreshToken.getAccessToken(
                                            refreshToken, clientId, clientSecret);
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.sasl.enable", "true");
            props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
            props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);

            Session session = Session.getInstance(props);
            session.setDebug(true);
            
            SMTPTransport smtpTransport = connectToSmtp(session, host, port,
                                                userEmail, oauthToken, true);

            Message message = new MimeMessage(session);
            message.setSubject("Activate your DocSchedule account");
            message.setText("Dear " + name + ",\n\n" + body);

            Address toAddress = new InternetAddress(toEmail);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch (MessagingException e) {
            System.out.println("Messaging Exception");
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Messaging Exception");
            System.out.println("Error: " + e.getMessage());
        }

    }
}
