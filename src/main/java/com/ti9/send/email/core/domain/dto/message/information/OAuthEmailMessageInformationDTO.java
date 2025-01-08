package com.ti9.send.email.core.domain.dto.message.information;

import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthEmailMessageInformationDTO extends EmailMessageInformationDTO {
    private OAuthSettings oAuthSettings;

    public OAuthEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO
    ) {
        super(
                emailMessageInformationDTO.getFrom(),
                emailMessageInformationDTO.getToList(),
                emailMessageInformationDTO.getBody(),
                emailMessageInformationDTO.getAttachment(),
                emailMessageInformationDTO.getSubject(),
                emailMessageInformationDTO.getCarbonCopy(),
                emailMessageInformationDTO.getBlindCarbonCopy(),
                emailMessageInformationDTO.getProviderType()
        );
    }
}
