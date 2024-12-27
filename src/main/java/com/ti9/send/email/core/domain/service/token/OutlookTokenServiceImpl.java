package com.ti9.send.email.core.domain.service.token;

import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("outlookTokenService")
public class OutlookTokenServiceImpl implements TokenService {

    @Override
    public void validateAndRenewToken(TokenDTO tokenDTO) {

    }

    @Override
    public UserInformationDTO getDecodedToken(TokenDTO tokenDTO) {
        return null;
    }
}
