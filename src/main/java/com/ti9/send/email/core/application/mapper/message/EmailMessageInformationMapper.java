package com.ti9.send.email.core.application.mapper.message;

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
            List<byte[]> attachments
    ) {
        EmailMessageInformationDTO emailMessageInformationDTO = new EmailMessageInformationDTO();
        emailMessageInformationDTO.setFrom(messageRule.getMessageTemplate().getAccount().getSettings());
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
            DocumentDTO documentDTO,
            MessageRule messageRule,
            String body,
            List<byte[]> attachments
    ) {
        SMTPEmailMessageInformationDTO emailMessageInformationDTO =
                (SMTPEmailMessageInformationDTO) EmailMessageInformationMapper.emailMessageInformationDTO(
                        documentDTO,
                        messageRule,
                        body,
                        attachments
                );
        emailMessageInformationDTO.setUserName("barba");
        emailMessageInformationDTO.setPassword("123");
        return emailMessageInformationDTO;
    }

    public static OAuthEmailMessageInformationDTO toOAuthEmailMessageInformationDTO(
            DocumentDTO documentDTO,
            MessageRule messageRule,
            String body,
            List<byte[]> attachments
    ) {
        OAuthEmailMessageInformationDTO emailMessageInformationDTO =
                (OAuthEmailMessageInformationDTO) EmailMessageInformationMapper.emailMessageInformationDTO(
                        documentDTO,
                        messageRule,
                        body,
                        attachments
                );
        emailMessageInformationDTO.setToken("barba");
        return emailMessageInformationDTO;
    }


}
