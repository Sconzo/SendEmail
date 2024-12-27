package com.ti9.send.email.core.application.mapper.message;

import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.SMTPEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;

import java.util.Collections;
import java.util.List;

public class EmailMessageInformationMapper {

    public static EmailMessageInformationDTO emailMessageInformationDTO(
            DocumentDTO documentDTO,
            MessageRule messageRule,
            String body,
            List<byte[]> attachments,
            UserInformationDTO userInformationDTO
    ) {
        EmailMessageInformationDTO emailMessageInformationDTO = new EmailMessageInformationDTO();
        emailMessageInformationDTO.setFrom(userInformationDTO.getEmail());
        emailMessageInformationDTO.setToList(Collections.singletonList(documentDTO.billingContact()));
        emailMessageInformationDTO.setBody(body);
        emailMessageInformationDTO.setAttachment(attachments);
        emailMessageInformationDTO.setCarbonCopy(messageRule.getMessageTemplate().getCc());
        emailMessageInformationDTO.setBlindCarbonCopy(messageRule.getMessageTemplate().getBcc());
        emailMessageInformationDTO.setSubject(messageRule.getMessageTemplate().getSubject());
        emailMessageInformationDTO.setProviderType(messageRule.getMessageTemplate().getAccount().getProvider());

        return emailMessageInformationDTO;
    }

    public static SMTPEmailMessageInformationDTO toSMTPEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO
    ) {
        SMTPEmailMessageInformationDTO smtpEmailMessageInformationDTO = new SMTPEmailMessageInformationDTO(
                emailMessageInformationDTO
        );
        smtpEmailMessageInformationDTO.setUserName("barba");
        smtpEmailMessageInformationDTO.setPassword("123");
        return smtpEmailMessageInformationDTO;
    }

    public static OAuthEmailMessageInformationDTO toOAuthEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO,
            TokenDTO tokenDTO
    ) {
        OAuthEmailMessageInformationDTO oAuthEmailMessageInformationDTO = new OAuthEmailMessageInformationDTO(
                emailMessageInformationDTO
        );
        oAuthEmailMessageInformationDTO.setToken(
                tokenDTO
        );
        return oAuthEmailMessageInformationDTO;
    }


}
