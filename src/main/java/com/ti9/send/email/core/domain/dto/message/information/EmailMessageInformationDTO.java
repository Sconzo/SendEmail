package com.ti9.send.email.core.domain.dto.message.information;

import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageInformationDTO extends MessageInformationDTO {
    private String subject;
    private List<String> carbonCopy;
    private List<String> blindCarbonCopy;
    private ProviderEnum providerType;

    public EmailMessageInformationDTO(
            String from,
            List<String> toList,
            String body,
            List<File> attachment,
            String subject,
            List<String> carbonCopy,
            List<String> blindCarbonCopy,
            ProviderEnum providerType
    ) {
        super(from, toList, body, attachment);
        this.subject = subject;
        this.carbonCopy = carbonCopy;
        this.blindCarbonCopy = blindCarbonCopy;
        this.providerType = providerType;
    }
}
