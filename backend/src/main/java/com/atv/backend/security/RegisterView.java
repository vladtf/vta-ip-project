package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class RegisterView {

    private final UserProvider userProvider;

    @Autowired
    public RegisterView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

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


    public static boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }


    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm) {
        // if (StringUtils.isNotBlank(registerForm.username()) &&
        // StringUtils.isNotBlank(registerForm.password()) &&
        // StringUtils.isNotBlank(registerForm.email()) &&
        // StringUtils.isNotBlank(registerForm.phoneNumber())) {
        // registeredUsers.add(registerForm.getUsername());

        if (validateUsername(registerForm.username()) && validatePassword(registerForm.password())
                && validateEmail(registerForm.email())) {
            userProvider.addRegisteredUser(registerForm.username());
            return "success";
        }

        List<String> registeredUsers = userProvider.getRegisteredUsers();
        System.out.println(registeredUsers);
        return "error";
    }

    // Getter for registered users LinkedList
    @GetMapping("/registeredUsers")
    public List<String> getRegisteredUsers() {
        return userProvider.getRegisteredUsers();
    }

}
