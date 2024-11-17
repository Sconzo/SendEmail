package com.ti9.send.email.core.domain.dto.account;

import core.domain.model.account.ProviderEnum;

public record AssociateAccountRequest (
        String name,
        ProviderEnum provider,
        AccountSettings accountSettings
){
}
