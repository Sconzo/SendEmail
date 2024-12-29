package com.ti9.send.email.core.infrastructure.adapter.out.repository.account;

import com.ti9.send.email.core.domain.model.account.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<Account, UUID> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE ecob_account " +
            " SET status = CASE " +
            " WHEN status = 'ACTIVE' THEN 'INACTIVE' " +
            " WHEN status = 'INACTIVE' THEN 'ACTIVE' " +
            " END" +
            " WHERE id = :uuid", nativeQuery = true)
    void changeStatus(UUID uuid);
}
