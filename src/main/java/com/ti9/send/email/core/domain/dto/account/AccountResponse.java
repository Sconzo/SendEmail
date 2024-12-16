package com.ti9.send.email.core.domain.dto.account;

import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;

import java.util.UUID;

public record AccountResponse(
        UUID accountId,
        String name,
        ProviderEnum provider,
        StatusEnum status
) {
}
