package com.atv.backend.security;

import com.atv.backend.providers.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/register")
public class RegisterView {


    // TODO: map
    // private static LinkedList<String> registeredUsers = new LinkedList<>();
    private Map<String, String> registeredUsers = new HashMap<>();


    private final UserProvider userProvider;

    @Autowired
    public RegisterView(UserProvider userProvider) {
        this.userProvider = userProvider;
    }


    @PostMapping("")
    public String register(@RequestBody RegisterForm registerForm) {

        if (Validator.validateUsername(registerForm.username()) && Validator.validatePassword(registerForm.password())
                && Validator.validateEmail(registerForm.email()) && Validator.validatePhoneNumber(registerForm.phoneNumber())) {
            userProvider.addRegisteredUser(registerForm.getUsername());
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
        return userProvider.getRegisteredUsers();
    }

}
