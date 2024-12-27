package com.ti9.send.email.core.domain.dto.message.information;

import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMTPEmailMessageInformationDTO extends EmailMessageInformationDTO {
    private String userName;
    private String password;

    public SMTPEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO
    ) {
        super(
                emailMessageInformationDTO.getSubject(),
                emailMessageInformationDTO.getCarbonCopy(),
                emailMessageInformationDTO.getBlindCarbonCopy(),
                emailMessageInformationDTO.getProviderType()
        );
    }
}
