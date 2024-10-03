package com.grex.email.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class LocalEmailService implements EmailService {

    @Override
    public void sendEmail(String from, String to, String subject, String body) {
       // do nothing in dev profile.
    }
}
