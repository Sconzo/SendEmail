package com.ti9.send.email.core.domain.dto.message.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthEmailMessageInformationDTO extends EmailMessageInformationDTO {
    private TokenDTO token;

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
