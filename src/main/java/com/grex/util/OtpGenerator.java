package com.grex.util;

import java.security.SecureRandom;

public class OtpGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int digits = 8;

    public static String generateOtp(){

        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < digits; i++) {
            otp.append(CHARACTERS.charAt(new SecureRandom().nextInt(CHARACTERS.length())));
        }

        return otp.toString();
    }
}
