package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
import com.atv.backend.requests.LoginRequest;
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
    public String login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = Validator.validateEmail(loginRequest.getEmail()) && Validator.validatePassword(loginRequest.getPassword());

        if (!isValid) {
            return "error";
        }

        if (!userService.userExists(loginRequest)) {
            return "error";
        }

        return userService.login(loginRequest).getToken();
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
