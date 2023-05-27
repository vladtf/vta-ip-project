package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.Transaction;
import com.atv.backend.dao.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> findAllByDestAccount(Account destAccount);

    List<Transaction> findAllBySourceAccount(Account sourceAccount);
}
