
package com.atv.backend.security;

import com.atv.backend.dao.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atv.backend.providers.UserProvider;
import com.atv.backend.dao.services.UserService;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.services.AccountService;


import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.Account;

import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.security.LoginForm;
import com.atv.backend.security.RegisterForm;
import org.springframework.stereotype.Service;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController2 {

    private final UserService userService;

    @Autowired
    private AccountService accountService;


    @Autowired
    public UserController2(UserService userService,AccountService accountService) {
        this.userService = userService;
        this.accountService=accountService;
    }


    @PostMapping("/registerUser")
    public User registerUser(@RequestBody RegisterForm registerForm) {
        return userService.registerUser(registerForm);
    }


    @PostMapping("/accountsp")
    public List<Account> createAccountForUserByToken(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        return accountService.createAccountForUserByToken(token, account);
    }

    @GetMapping("/accountsg")
    public List<Account> getUserAccountsByToken(@RequestHeader("Authorization") String token) {
        return accountService.getUserAccountsByToken(token);
    }

}