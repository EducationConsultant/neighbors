package com.education.consultant.educon.service.impl;

import com.education.consultant.educon.document.User;
import com.education.consultant.educon.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(User user) {
        final String username = "djolepingivin@gmail.com";
        final String password = "djolejepingvin12";

        final String messageText = String.format(
                "Hello " + user.getFirstName()
                        + ", \n\nYour password is: " + user.getPassword()
                        + "\n\nThe Education Consultant Team");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "*");

        // Get the Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
          //  message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));

            // Set Subject: header field
            message.setSubject("Education Consultant: Forgot password");

            // Now set the actual message
            message.setText(messageText);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
