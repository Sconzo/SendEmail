package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.domain.dto.GenericWrapper;
import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.SMTPEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.service.token.TokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.Properties;

@Component
public class SenderEmailImpl implements Sender<EmailMessageInformationDTO> {

    private final Map<ProviderEnum, JavaMailSender> mailSenders;
    private final Map<ProviderEnum, TokenService> tokenServiceMap;

    @Autowired
    public SenderEmailImpl(
            @Qualifier("oAuthGmailMailSender") JavaMailSender oAuthGmailMailSender,
            @Qualifier("outlookMailSender") JavaMailSender outlookSender,
            @Qualifier("smtpGmailMailSender") JavaMailSender smtpGmailMailSender,
            @Qualifier("gmailTokenService") TokenService gmailTokenService,
            @Qualifier("outlookTokenService") TokenService outlookTokenService
    ) {
        this.mailSenders = Map.of(
                ProviderEnum.GMAIL, oAuthGmailMailSender,
                ProviderEnum.OUTLOOK, outlookSender,
                ProviderEnum.SMTP, smtpGmailMailSender
        );
        this.tokenServiceMap = Map.of(
                ProviderEnum.GMAIL, gmailTokenService,
                ProviderEnum.OUTLOOK, outlookTokenService
        );
    }

    @Override
    public void send(
            EmailMessageInformationDTO emailMessageInformationDTO
    ) throws MessagingException {
        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) mailSenders.get(
                emailMessageInformationDTO.getProviderType()
        );
        switch (emailMessageInformationDTO.getProviderType()) {
            case GMAIL, OUTLOOK -> oAuthConfiguration(
                    javaMailSender,
                    (OAuthEmailMessageInformationDTO) emailMessageInformationDTO,
                    tokenServiceMap.get(emailMessageInformationDTO.getProviderType())
            );
            case SMTP -> smtpConfiguration(
                    javaMailSender,
                    (SMTPEmailMessageInformationDTO) emailMessageInformationDTO
            );
            default -> throw new IllegalArgumentException("Invalid provider");
        }


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        helper.setFrom(emailMessageInformationDTO.getFrom());
        helper.setTo(emailMessageInformationDTO.getToList().toArray(new String[0]));
        helper.setSubject(emailMessageInformationDTO.getSubject());
        helper.setText(emailMessageInformationDTO.getBody(), true);
        helper.setCc(emailMessageInformationDTO.getCarbonCopy().toArray(new String[0]));
        helper.setBcc(emailMessageInformationDTO.getBlindCarbonCopy().toArray(new String[0]));
        for (File file : emailMessageInformationDTO.getAttachment()) {
            helper.addAttachment(file.getName(), file);
        }

        javaMailSender.send(mimeMessage);
    }

    private void oAuthConfiguration(
            JavaMailSenderImpl mailSender,
            OAuthEmailMessageInformationDTO emailMessageInformationDTO,
            TokenService tokenService
    ) {
        tokenService.validateAndRenewToken(new GenericWrapper<>(emailMessageInformationDTO.getOAuthSettings()));
        mailSender.setUsername(emailMessageInformationDTO.getFrom());
        mailSender.setPassword(null);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        props.put("mail.smtp.auth.xoauth2.accessToken", emailMessageInformationDTO.getOAuthSettings().getAccessToken());

        mailSender.setSession(jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(
                        emailMessageInformationDTO.getFrom(),
                        emailMessageInformationDTO.getOAuthSettings().getAccessToken()
                );
            }
        }));

    }

    private void smtpConfiguration(
            JavaMailSenderImpl mailSender,
            SMTPEmailMessageInformationDTO emailMessageInformationDTO
    ) {
        mailSender.setUsername(emailMessageInformationDTO.getFrom());
        mailSender.setPassword(emailMessageInformationDTO.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
    }
}
