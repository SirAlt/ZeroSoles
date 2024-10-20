package com.example.zerosoles.utils;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public final class Emails {

    private Emails() {}

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
