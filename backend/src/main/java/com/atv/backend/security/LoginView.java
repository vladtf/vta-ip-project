package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LoginView {


    private final UserProvider userProvider;

    @Autowired
    public LoginView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        if (StringUtils.isNotBlank(loginForm.username()) && StringUtils.isNotBlank(loginForm.password())) {
            userProvider.addRegisteredUser(loginForm.getUsername());
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
