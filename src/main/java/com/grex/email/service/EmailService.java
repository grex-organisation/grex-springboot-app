package com.grex.email.service;

public interface EmailService {
    void sendEmail(String from, String to, String subject, String body);
}
