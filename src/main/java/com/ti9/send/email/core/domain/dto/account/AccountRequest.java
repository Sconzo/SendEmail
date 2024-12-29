package com.ti9.send.email.core.domain.dto.account;

import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotEmpty(message = "Name cannot be empty")
        String name,
        @NotEmpty(message = "Provider cannot be empty")
        ProviderEnum provider,
        @NotEmpty(message = "Status cannot be empty")
        StatusEnum status,
        @NotNull(message = "Settings cannot be null")
        AccountSettings settings
){}
