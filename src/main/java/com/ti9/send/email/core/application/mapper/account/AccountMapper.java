package com.ti9.send.email.core.application.mapper.account;

import com.ti9.send.email.core.domain.dto.account.AccountResponse;
import com.ti9.send.email.core.domain.model.account.Account;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getProvider(),
                account.getStatus()
        );
    }
}
