
package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private AccountService accountService;


    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterForm registerForm) {
        return userService.registerUser(registerForm);
    }

    @PostMapping("/accounts")
    public List<Account> createAccountForUserByToken(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        return accountService.createAccountForUserByToken(token, account);
    }

    @GetMapping("/accounts")
    public List<Account> getUserAccountsByToken(@RequestHeader("Authorization") String token) {
        return accountService.getUserAccountsByToken(token);
    }
}