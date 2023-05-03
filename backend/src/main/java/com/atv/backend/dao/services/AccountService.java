package com.atv.backend.dao.services;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.AccountRepository;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(UserRepository userRepository, TokenRepository tokenRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.accountRepository = accountRepository;
    }

    private User getUserByToken(String token) {
        Token updatedToken = tokenRepository.findByToken(token);
        return updatedToken.getUser();
    }


    public List<Account> getUserAccountsByToken(String token) {
        User user = getUserByToken(token);
        return user.getAccounts();
    }

    public List<Account> createAccountForUserByToken(String token, Account account) {
        User user = getUserByToken(token);
        user.getAccounts().add(account);
        userRepository.save(user);

        account.setUser(user);
        accountRepository.save(account);

        return user.getAccounts();
    }
}
