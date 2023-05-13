package com.atv.backend.dao.services;


import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.repositories.AccountRepository;
import com.atv.backend.requests.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void makeTransaction(TransactionRequest transactionRequest) {
        Optional <Account> sourceAccountOpt = accountRepository.findAccountByIban(transactionRequest.getIbanSource());
        Optional<Account> destAccountOpt = accountRepository.findAccountByIban(transactionRequest.getIbanDest());
        Double sum = transactionRequest.getSum();

        if(sourceAccountOpt.isEmpty()){
            throw new RuntimeException("source account is empty");
        }
        if(destAccountOpt.isEmpty()){
            throw new RuntimeException("dest account is empty");
        }

        Account sourceAccount=sourceAccountOpt.get();
        Account destAccount = destAccountOpt.get();

        if (sourceAccount.getBalance() < sum) {
            throw new RuntimeException("Not enough balance");
        }
        sourceAccount.setBalance(sourceAccount.getBalance() - sum);
        destAccount.setBalance(destAccount.getBalance() + sum);

        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);

    }
}
