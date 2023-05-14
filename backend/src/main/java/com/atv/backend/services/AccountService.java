package com.atv.backend.services;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.AccountRepository;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }


    public List<Account> getUserAccountsByToken(String token) {
        User user = userService.getUserByToken(token);
        //return user.getAccounts();

        List<Account> accounts = user.getAccounts();
        for (Account account1 : accounts) {
            account1.setUser(null);
        }
        return accounts;
    }

    public List<Account> createAccountForUserByToken(String token, Account account) {
        User user = userService.getUserByToken(token);
       // user.getAccounts().add(account);
        account.setUser(user);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAccountsByUser(user);

//        for (Account account1 : accounts) {
//            account1.setUser(null);
//        }

        return accounts;
    }
}
