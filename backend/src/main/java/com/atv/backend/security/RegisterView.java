package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
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


    // TODO: map
    // private static LinkedList<String> registeredUsers = new LinkedList<>();
    private Map<String, String> registeredUsers =  new HashMap<>(); ;


    private final UserProvider userProvider;

    @Autowired
    public RegisterView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }



    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm) {

        if (Validator.validateUsername(registerForm.username()) && Validator.validatePassword(registerForm.password())
                &&Validator.validateEmail(registerForm.email()) && Validator.validatePhoneNumber(registerForm.phoneNumber())) {
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
