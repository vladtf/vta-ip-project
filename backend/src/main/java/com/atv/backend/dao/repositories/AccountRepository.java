package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
