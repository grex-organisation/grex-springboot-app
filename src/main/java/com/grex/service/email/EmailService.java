package com.grex.service.email;

public interface EmailService {
    void sendEmail(String from, String to, String subject, String body);
}
