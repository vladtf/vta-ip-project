package com.atv.backend.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;


@RestController
public class LoginView {

    // LinkedList to store logged users
    private static LinkedList<String> loggedUsers = new LinkedList<>(); ///map

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        if (StringUtils.isNotBlank(loginForm.username()) && StringUtils.isNotBlank(loginForm.password())) {
            loggedUsers.add(loginForm.getUsername());
            return "success";
        }
        return "error";
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }

    // Getter for loggedUsers LinkedList
    @GetMapping("/loggedUsers")
    public LinkedList<String> getLoggedUsers() {
        return loggedUsers;
    }

}
