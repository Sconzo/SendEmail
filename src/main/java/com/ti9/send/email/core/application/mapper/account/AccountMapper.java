package com.ti9.send.email.core.application.mapper.account;

import com.google.gson.Gson;
import com.ti9.send.email.core.domain.dto.account.AccountRequest;
import com.ti9.send.email.core.domain.dto.account.AccountResponse;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.infrastructure.adapter.utils.ConverterUtils;

import java.util.Objects;

public class AccountMapper {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getProvider(),
                account.getStatus()
        );
    }

    public static Account toEntity(AccountRequest body) {
        Gson gson = new Gson();
        Account account = new Account();

        account.setName(body.name());
        account.setProvider(body.provider());
        account.setStatus(body.status());
        account.setSettings(body.name());
        String settings = null;

        if (Objects.nonNull(body.settings())) {
            settings = ConverterUtils.serializeFirstNonNull(
                    gson,
                    body.settings()
            );
        }

        account.setSettings(settings);

        return account;
    }


}
