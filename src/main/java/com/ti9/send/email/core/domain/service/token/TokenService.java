package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;

public interface TokenService {

    void validateAndRenewToken(OAuthSettings tokenDTO);

    UserInformationDTO getDecodedToken(OAuthSettings tokenDTO);
}
