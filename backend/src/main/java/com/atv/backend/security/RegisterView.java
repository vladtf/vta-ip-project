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

    
    @PostMapping("/register")
    public String register(@RequestBody RegisterForm registerForm) {
        if (StringUtils.isNotBlank(registerForm.username()) && StringUtils.isNotBlank(registerForm.password()) && StringUtils.isNotBlank(registerForm.email()) && StringUtils.isNotBlank(registerForm.phoneNumber())) {
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
