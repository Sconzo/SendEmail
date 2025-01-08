package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import org.springframework.stereotype.Service;

@Service("outlookTokenService")
public class OutlookTokenServiceImpl implements TokenService {

    @Override
    public void validateAndRenewToken(OAuthSettings tokenDTO) {

    }

    @Override
    public UserInformationDTO getDecodedToken(OAuthSettings tokenDTO) {
        return null;
    }
}
