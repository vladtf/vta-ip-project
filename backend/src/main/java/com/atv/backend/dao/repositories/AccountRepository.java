package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.Account;
import com.atv.backend.dao.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findAccountByIban(String iban);

    List<Account> findAccountsByUser(User user);

    void deleteAccountByIban(String iban);

}
