package com.ti9.send.email.core.infrastructure.adapter.out.repository.account;

import com.ti9.send.email.core.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<Account, UUID> {


    @Query("select new com.ti9.send.email.core.domain.model.account.Account(" +
            " a.id, " +
            " a.name, " +
            " a.provider, " +
            " a.status " +
            " ) from Account a ")
    List<Account> findAllWithoutRelations();
}
