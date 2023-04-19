package com.atv.backend.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class RegisterView {

    // LinkedList to store logged users
    private static LinkedList<String> registeredUsers = new LinkedList<>();

    public static boolean validateUsername(String username) {

        int minAllowedLength = 5;
        int maxAllowedLength = 20;

        // First check: null / empty
        if (username == null || username.isEmpty()) {
            return false;
        }

        // Second check: length
        if (username.length() < minAllowedLength || username.length() > maxAllowedLength) {
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

        int minAllowedLength = 10;
        int maxAllowedLength = 16;

        // First check:null/ empty
        if (password == null || password.isEmpty())
            return false;

        // Second check : length
        int passwordLength = password.length();
        if (passwordLength < minAllowedLength || passwordLength > maxAllowedLength)
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

    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm) {
        // if (StringUtils.isNotBlank(registerForm.username()) &&
        // StringUtils.isNotBlank(registerForm.password()) &&
        // StringUtils.isNotBlank(registerForm.email()) &&
        // StringUtils.isNotBlank(registerForm.phoneNumber())) {
        // registeredUsers.add(registerForm.getUsername());

        if (validateUsername(registerForm.username()) && validatePassword(registerForm.password())) {
            registeredUsers.add(registerForm.getUsername());
            return "success";
        }
        return "error";
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }

    // Getter for registered users LinkedList
    @GetMapping("/registeredUsers")
    public LinkedList<String> getRegisteredUsers() {
        return registeredUsers;
    }

}
