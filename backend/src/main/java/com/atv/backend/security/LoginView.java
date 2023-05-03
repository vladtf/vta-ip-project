package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginView {

    private final UserService userService;

    @Autowired
    public LoginView(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String login(@RequestBody LoginForm loginForm) {
        boolean isValid = Validator.validateEmail(loginForm.getEmail()) && Validator.validatePassword(loginForm.getPassword());

        if (!isValid) {
            return "error";
        }

        if (!userService.userExists(loginForm)) {
            return "error";
        }

        return userService.login(loginForm).getToken();
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }

    // Getter for loggedUsers LinkedList
    @GetMapping("/users")
    public List<String> getLoggedUsers() {
        return userService.getRegisteredUsers();
    }

}
