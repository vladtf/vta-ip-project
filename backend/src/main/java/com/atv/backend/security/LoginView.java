package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginView {


    private final UserProvider userProvider;

    @Autowired
    public LoginView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @PostMapping("")
    public String login(@RequestBody LoginForm loginForm) {
        if (Validator.validateUsername(loginForm.username()) && Validator.validatePassword(loginForm.password())) {
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
    @GetMapping("/users")
    public List<String> getLoggedUsers() {
        return userProvider.getRegisteredUsers();
    }

}
