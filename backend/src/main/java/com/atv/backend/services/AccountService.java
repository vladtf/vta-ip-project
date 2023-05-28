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
        accounts.forEach(account1 -> account1.setUser(null));

        return accounts;
    }

    public List<UserController.TransactionResponse> findTransactionsByIban(String iban, User user) {

        if (iban == null) {
            List<UserController.TransactionResponse> allTransactions = new ArrayList<>();

            List<Account> accounts = accountRepository.findAccountsByUser(user);

            for (Account account : accounts) {
                allTransactions.addAll(findTransactionsByIban(account.getIban(), user));
            }

            return allTransactions;
        }

        Optional<Account> accountOptional = accountRepository.findAccountByIban(iban);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found.");
        }

        Account account = accountOptional.get();
        List<Transaction> allByDestAccount = transactionRepository.findAllByDestAccount(account);
        List<Transaction> allBySourceAccount = transactionRepository.findAllBySourceAccount(account);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(allByDestAccount);
        allTransactions.addAll(allBySourceAccount);

        List<UserController.TransactionResponse> results = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            TransactionType transactionType = (transaction.getSourceAccount().getIban().equals(iban))
                    ? TransactionType.OUTCOME : TransactionType.INCOME;

            String sourceAccount = (transaction.getSourceAccount().getIban().equals(iban))
                    ? transaction.getSourceAccount().getIban() : transaction.getDestAccount().getIban();

            results.add(new UserController.TransactionResponse(
                    transaction.getSum(),
                    sourceAccount, transaction.getDestAccount().getIban(),
                    transactionType,
                    transaction.getCreatedAt()
            ));
        }

        return results;
    }


    public List<Account> deleteAccountForUserByToken(String token, Account account) {
        User user = userService.getUserByToken(token);

        if (account.getIban() == null || account.getIban().equals("")) {
            throw new RuntimeException("iban not provided");
        }

        Optional<Account> accountOptional = accountRepository.findAccountByIban(account.getIban());
        if (accountOptional.isEmpty())
            throw new RuntimeException("account not found.");

        Account accountToDelete = accountOptional.get();
        if (!accountToDelete.getUser().getId().equals(user.getId()))
            throw new RuntimeException("account not found.");

        accountToDelete.setUser(null);
        accountRepository.delete(accountToDelete);

        List<Account> accounts = accountRepository.findAccountsByUser(user);
        accounts.forEach(account1 -> account1.setUser(null));

        return accounts;
    }

    public List<Account> getAllAccounts(String token) {
        Iterable<Account> all = accountRepository.findAll();
        List<Account> allAccounts = new ArrayList<>();
        all.forEach(allAccounts::add);
        allAccounts.forEach(account -> account.setUser(null));
        return allAccounts;
    }

    public boolean updateAccount(String token, Account account) {
        Account accountToUpdate = accountRepository.findAccountByIban(account.getIban()).orElseThrow(() -> new RuntimeException("Account not found."));

        if (accountToUpdate.getUser() == null) {
            throw new RuntimeException("Account not found.");
        }

        accountToUpdate.setBalance(account.getBalance());

        accountRepository.save(accountToUpdate);
        return true;
    }
}
