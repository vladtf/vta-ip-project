package com.atv.backend.services;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @Test
    public void testCreateUserAndAddAccountUsingTheToken() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("test");
        registerRequest.setLastName("test");
        registerRequest.setEmail("test123@email.com");
        registerRequest.setPassword("test");
        registerRequest.setPhone("123456789");

        // register user
        User registerUser = userService.registerUser(registerRequest);
        Assert.assertNotNull(registerUser);

        // check if user exists
        boolean userExists = userService.userExists(registerRequest.getEmail());
        Assert.assertTrue(userExists);

        // login user
        Token token = userService.login(new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword(), "test"));
        Assert.assertNotNull(token);

        // create account
        Account account = new Account();
        account.setIban("RO123456789");
        account.setBalance(1000.0);
        account.setCurrency(Account.Currency.RON);


        // add account to user
        List<Account> accountForUserByToken = accountService.createAccountForUserByToken(token.getToken(), account);
        Assert.assertNotNull(accountForUserByToken);
        Assert.assertEquals(1, accountForUserByToken.size());

        // get accounts for user
        List<Account> accountsForUserByToken = accountService.getUserAccountsByToken(token.getToken());
        Assert.assertNotNull(accountsForUserByToken);
        Assert.assertEquals(1, accountsForUserByToken.size());
    }

}