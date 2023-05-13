package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
import com.atv.backend.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/register")
public class RegisterView {


    private final UserService userService;

    @Autowired
    public RegisterView(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("")
    public String register(@RequestBody RegisterRequest registerRequest) {
        if (Validator.validateUsername(registerRequest.getFirstName()) &&
                Validator.validateUsername(registerRequest.getLastName()) &&
                Validator.validatePassword(registerRequest.getPassword()) &&
                Validator.validateEmail(registerRequest.getEmail()) &&
                Validator.validatePhoneNumber(registerRequest.getPhone())) {

            if (userService.userExists(registerRequest.getEmail())) {
                return "user already exists";
            }

            userService.registerUser(registerRequest);
            return "success";
        }
        return "error";
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }

    // Getter for registered users LinkedList
    @GetMapping("/users")
    public List<String> getRegisteredUsers() {
        return userService.getRegisteredUsers();
    }

}
