package com.atv.backend.services;

import com.atv.backend.controllers.TransactionType;
import com.atv.backend.controllers.UserController;
import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Transaction;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.AccountRepository;
import com.atv.backend.dao.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
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

    public List<UserController.TransactionResponse> findTransactionsByIban(String iban) {

        Optional<Account> accountOptional = accountRepository.findAccountByIban(iban);
        if(accountOptional.isEmpty())
            throw new RuntimeException("account not found.");

        Account  account = accountOptional.get();
        List<Transaction> allByDestAccount = transactionRepository.findAllByDestAccount(account);
        List<Transaction> allBySourceAccount = transactionRepository.findAllBySourceAccount(account);

        List <UserController.TransactionResponse> results=new ArrayList<>();
        for (Transaction transaction : allByDestAccount) {
            results.add(new UserController.TransactionResponse(transaction.getSum(), transaction.getDestAccount().getIban(), TransactionType.ADD, transaction.getCreatedAt()));
        }

        for (Transaction transaction : allBySourceAccount) {
            results.add(new UserController.TransactionResponse(transaction.getSum(), transaction.getDestAccount().getIban(), TransactionType.SUBSTRACT, transaction.getCreatedAt()));
        }

        return results;
    }


}
