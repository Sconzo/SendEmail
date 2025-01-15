package com.ti9.send.email.core.infrastructure.adapter.out.repository.account;

import com.ti9.send.email.core.application.port.out.account.AccountRepository;
import com.ti9.send.email.core.domain.model.account.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaRepository;
    private final AccountJdbcRepository jdbcRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaRepository, AccountJdbcRepository jdbcRepository) {
        this.jpaRepository = jpaRepository;
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return jpaRepository.save(account);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Account> list() {
        return jpaRepository.findAll();
    }

    @Override
    public void changeStatus(UUID uuid) {
        jpaRepository.changeStatus(uuid);
    }

    @Override
    public void updateAccountSettings(UUID id, String settings) {
        jdbcRepository.updateAccountSettings(id, settings);
    }
}
