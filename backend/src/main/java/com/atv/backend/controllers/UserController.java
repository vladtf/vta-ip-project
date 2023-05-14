
package com.atv.backend.controllers;

import com.atv.backend.dao.entities.Token;
import com.atv.backend.services.TransactionService;
import com.atv.backend.services.UserService;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import com.atv.backend.requests.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atv.backend.dao.entities.User;
import com.atv.backend.services.AccountService;


import com.atv.backend.dao.entities.Account;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final TransactionService transactionService;
    private final AccountService accountService;


    @Autowired
    public UserController(UserService userService, TransactionService transactionService, AccountService accountService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountService = accountService;

    }

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest registerRequest) {
        if(registerRequest.getEmail()==null|| registerRequest.getEmail().equals("")){
            throw new RuntimeException("email not provided");
        }

        return userService.registerUser(registerRequest);
    }


    @PostMapping("/accounts")
    public List<Account> createAccountForUserByToken(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        if(account.getIban()==null|| account.getIban().equals("")){
            throw new RuntimeException("iban not provided");
        }
        return accountService.createAccountForUserByToken(token, account);
    }

    @GetMapping("/accounts")
    public List<Account> getUserAccountsByToken(@RequestHeader("Authorization") String token) {
        if(token==null||token.equals(""))
            throw new RuntimeException("token doesn t exist");
        return accountService.getUserAccountsByToken(token);
    }

    @PostMapping("/login")
    public TokenResponse loginUser(@RequestBody LoginRequest loginRequest) {
        Token token = userService.login(loginRequest);
        System.out.println("in login");
        return new TokenResponse(token.getToken());
    }


    @PostMapping("/transaction")
    public String makeTransaction(@RequestBody TransactionRequest transactionRequest,@RequestHeader("Authorization") String token) {

        if(token==null||token.equals(""))
            throw new RuntimeException("token doesn t exist");

        transactionService.makeTransaction(transactionRequest);
        return "succes";

    }





    private static class TokenResponse {
        private final String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}