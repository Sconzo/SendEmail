package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.GenericWrapper;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.model.account.Account;

public interface TokenService {

    void validateAndRenewToken(GenericWrapper<? extends AccountSettings> accountSettings);

    UserInformationDTO getDecodedToken(Account account);
}
