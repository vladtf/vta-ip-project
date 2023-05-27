
package com.atv.backend.controllers;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import com.atv.backend.requests.TransactionRequest;
import com.atv.backend.services.AccountService;
import com.atv.backend.services.TransactionService;
import com.atv.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        if (registerRequest.getEmail() == null || registerRequest.getEmail().equals("")) {
            throw new RuntimeException("email not provided");
        }

        return userService.registerUser(registerRequest);
    }


    @PostMapping("/accounts")
    public List<Account> createAccountForUserByToken(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        if (account.getIban() == null || account.getIban().equals("")) {
            throw new RuntimeException("iban not provided");
        }
        return accountService.createAccountForUserByToken(token, account);
    }

    @DeleteMapping("/accounts")
    public List<Account> deleteAccountForUserByToken(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        if (account.getIban() == null || account.getIban().equals("")) {
            throw new RuntimeException("iban not provided");
        }
        return accountService.deleteAccountForUserByToken(token, account);
    }

    @GetMapping("/accounts")
    public List<Account> getUserAccountsByToken(@RequestHeader("Authorization") String token) {
        if (token == null || token.equals(""))
            throw new RuntimeException("token doesn t exist");
        return accountService.getUserAccountsByToken(token);
    }

    @PostMapping("/login")
    public TokenResponse loginUser(@RequestBody LoginRequest loginRequest) {
        Token token = userService.login(loginRequest);
        return new TokenResponse(token.getToken());
    }


    @PostMapping("/transaction")
    public String makeTransaction(@RequestBody TransactionRequest transactionRequest, @RequestHeader("Authorization") String token) {

        if (token == null || token.equals(""))
            throw new RuntimeException("token doesn t exist");

        transactionService.makeTransaction(transactionRequest);
        return "succes";
    }

    @GetMapping("/emails")
    public List<String> getAllEmails(@RequestHeader("Authorization") String token) {
        return userService.getAllEmails(token);
    }

    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactions(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "iban", required = false) String iban
    ) {
        if (token == null || token.equals("")) {
            throw new RuntimeException("User not logged in");
        }

        User user = userService.getUserByToken(token);

        return accountService.findTransactionsByIban(iban, user);
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


    public static class TransactionResponse {
        private final Double sum;
        private final String sourceAccount;
        private final String destAccount;
        private final TransactionType transactionType;

        private final Date createdAt;

        public Double getSum() {
            return sum;
        }

        public String getSourceAccount() {
            return sourceAccount;
        }

        public String getDestAccount() {
            return destAccount;
        }

        public TransactionType getTransactionType() {
            return transactionType;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public TransactionResponse(Double sum, String sourceAccount, String destAccount, TransactionType transactionType, Date createdAt) {
            this.sum = sum;
            this.sourceAccount = sourceAccount;
            this.destAccount = destAccount;
            this.transactionType = transactionType;
            this.createdAt = createdAt;
        }

    }

}

