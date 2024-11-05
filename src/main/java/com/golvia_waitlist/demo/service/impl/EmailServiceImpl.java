package com.golvia_waitlist.demo.service.impl;

import com.golvia_waitlist.demo.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${spring.mail.username}")
    private String senderEmail; // Your sender email address


    @Override
    public void sendEmail(String to, String subject, String body) {
        Email from = new Email(senderEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body); // For plain text email; change to "text/html" for HTML
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() == 202) {
                System.out.println("Email sent successfully to " + to);
            } else {
                System.out.println("Failed to send email, response code: " + response.getStatusCode());
            }
        } catch (IOException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
            throw new RuntimeException("Failed to send email", ex);
        }
    }
}