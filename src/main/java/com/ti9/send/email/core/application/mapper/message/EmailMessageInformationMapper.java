package com.ti9.send.email.core.application.mapper.message;

import com.ti9.send.email.core.domain.dto.account.OAuthSettings;
import com.ti9.send.email.core.domain.dto.account.SmtpSettings;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.SMTPEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class EmailMessageInformationMapper {

    public static EmailMessageInformationDTO emailMessageInformationDTO(
            DocumentDTO documentDTO,
            MessageRule messageRule,
            String body,
            List<File> attachments
    ) {
        EmailMessageInformationDTO emailMessageInformationDTO = new EmailMessageInformationDTO();
        emailMessageInformationDTO.setToList(Collections.singletonList(documentDTO.billingEmail()));
        emailMessageInformationDTO.setBody(body);
        emailMessageInformationDTO.setAttachment(attachments);
        emailMessageInformationDTO.setCarbonCopy(messageRule.getMessageTemplate().getCc());
        emailMessageInformationDTO.setBlindCarbonCopy(messageRule.getMessageTemplate().getBcc());
        emailMessageInformationDTO.setSubject(messageRule.getMessageTemplate().getSubject());
        emailMessageInformationDTO.setProviderType(messageRule.getMessageTemplate().getAccount().getProvider());

        return emailMessageInformationDTO;
    }

    public static SMTPEmailMessageInformationDTO toSMTPEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO,
            SmtpSettings smtpSettings
    ) {
        emailMessageInformationDTO.setFrom(smtpSettings.getUsername());
        SMTPEmailMessageInformationDTO smtpEmailMessageInformationDTO = new SMTPEmailMessageInformationDTO(
                emailMessageInformationDTO
        );
        smtpEmailMessageInformationDTO.setUsername(smtpSettings.getUsername());
        smtpEmailMessageInformationDTO.setPassword(smtpSettings.getPassword());
        return smtpEmailMessageInformationDTO;
    }

    public static OAuthEmailMessageInformationDTO toOAuthEmailMessageInformationDTO(
            EmailMessageInformationDTO emailMessageInformationDTO,
            OAuthSettings oAuthSettings,
            UserInformationDTO userInformationDTO
    ) {
        OAuthEmailMessageInformationDTO oAuthEmailMessageInformationDTO = new OAuthEmailMessageInformationDTO(
                emailMessageInformationDTO
        );
        oAuthEmailMessageInformationDTO.setFrom(userInformationDTO.getEmail());
        oAuthEmailMessageInformationDTO.setOAuthSettings(oAuthSettings);
        return oAuthEmailMessageInformationDTO;
    }


}
