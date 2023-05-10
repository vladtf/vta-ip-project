package com.atv.backend.dao.services;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.security.LoginForm;
import com.atv.backend.security.RegisterForm;
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
        RegisterForm registerForm = new RegisterForm();
        registerForm.setFirstName("test");
        registerForm.setLastName("test");
        registerForm.setEmail("test123@email.com");
        registerForm.setPassword("test");
        registerForm.setPhoneNumber("123456789");

        // register user
        User registerUser = userService.registerUser(registerForm);
        Assert.assertNotNull(registerUser);

        // check if user exists
        boolean userExists = userService.userExists(registerForm.getEmail());
        Assert.assertTrue(userExists);

        // login user
        Token token = userService.login(new LoginForm(registerForm.getEmail(), registerForm.getPassword()));
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