package com.grex.email.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("live")
public class AwsEmailService implements EmailService {


    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        // Use AWS SES to send email
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination()
                        .withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content().withCharset("UTF-8").withData(subject))
                        .withBody(new Body().withText(new Content().withCharset("UTF-8").withData(body))))
                .withSource(from);

        amazonSimpleEmailService.sendEmail(request);
    }

}
