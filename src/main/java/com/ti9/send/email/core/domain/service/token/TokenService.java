package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;

public interface TokenService {

    void validateAndRenewToken(TokenDTO tokenDTO);

    UserInformationDTO getDecodedToken(TokenDTO tokenDTO);
}
