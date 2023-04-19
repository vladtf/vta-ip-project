package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginView {


    private final UserProvider userProvider;

    @Autowired
    public LoginView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        if (Validator.validateUsername(loginForm.username()) && Validator.validatePassword(loginForm.password())) {
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
    public List<String> getLoggedUsers() {
        return userProvider.getRegisteredUsers();
    }

}
