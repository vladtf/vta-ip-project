package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
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
    public String register(@RequestBody RegisterForm registerForm) {
        if (Validator.validateUsername(registerForm.getFirstName()) &&
                Validator.validateUsername(registerForm.getLastName()) &&
                Validator.validatePassword(registerForm.getPassword()) &&
                Validator.validateEmail(registerForm.getEmail()) &&
                Validator.validatePhoneNumber(registerForm.getPhoneNumber())) {

            if (userService.userExists(registerForm.getEmail())) {
                return "user already exists";
            }

            userService.registerUser(registerForm);
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
