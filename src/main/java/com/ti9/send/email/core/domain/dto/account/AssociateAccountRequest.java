package com.ti9.send.email.core.domain.dto.account;

import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;

public record AssociateAccountRequest (
        String name,
        ProviderEnum provider,
        AccountSettings accountSettings
){
}
