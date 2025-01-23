package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.model.account.Account;

public interface TokenService {

    Account validateAndRenewToken(Account account);

    UserInformationDTO getDecodedToken(Account account);
}
