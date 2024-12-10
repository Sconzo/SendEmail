package com.ti9.send.email.core.domain.dto.message.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMTPEmailMessageInformationDTO extends EmailMessageInformationDTO {
    private String userName;
    private String password;
}
