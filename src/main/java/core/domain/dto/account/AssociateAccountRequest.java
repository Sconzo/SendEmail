package core.domain.dto.account;

import core.domain.model.account.ProviderEnum;

public record AssociateAccountRequest (
        String name,
        ProviderEnum provider,
        AccountSettings accountSettings
){
}
