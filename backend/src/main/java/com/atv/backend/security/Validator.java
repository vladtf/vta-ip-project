package com.atv.backend.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

    private static final int MIN_ALLOWED_PASSWORD = 10;
    private static final int MAX_ALLOWED_PASSWORD = 16;
    private static final int MIN_ALLOWED_USERNAME = 5;
    private static final int MAX_ALLOWED_USERNAME = 20;


    public static boolean validateUsername(String username) {

        // First check: null / empty
        if (username == null || username.isEmpty()) {
            return false;
        }

        // Second check: length
        if (username.length() < MIN_ALLOWED_USERNAME || username.length() > MAX_ALLOWED_USERNAME) {
            return false;
        }

        // Third check: invalid characters
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
        for (char c : username.toCharArray()) {
            if (allowedCharacters.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean validatePassword(String password) {

        // First check:null/ empty
        if (password == null || password.isEmpty())
            return false;

        // Second check : length
        int passwordLength = password.length();
        if (passwordLength < MIN_ALLOWED_PASSWORD || passwordLength > MAX_ALLOWED_PASSWORD)
            return false;

        // Third check: at least one uppercase letter, one lowercase letter, and one
        // digit
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*"))
            return false;

        // Fourth check: at least one special character
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
            return false;

        return true;
    }

    public static boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        String pattern = "^(\\+\\d{1,3})?\\d{10,15}$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
