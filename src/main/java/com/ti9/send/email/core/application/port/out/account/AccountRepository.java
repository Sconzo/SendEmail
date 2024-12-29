package com.ti9.send.email.core.application.port.out.account;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.account.Account;

import java.util.UUID;

public interface AccountRepository extends GenericRepository<Account, UUID> {
    void changeStatus(UUID uuid);
}
