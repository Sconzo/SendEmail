package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.SMTPEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component
public class SenderEmail implements Sender<EmailMessageInformationDTO> {

    private final Map<ProviderEnum, JavaMailSender> mailSenders;

    @Autowired
    public SenderEmail(
            @Qualifier("oAuthGmailMailSender") JavaMailSender oAuthGmailMailSender,
            @Qualifier("outlookMailSender") JavaMailSender outlookSender,
            @Qualifier("smtpGmailMailSender") JavaMailSender smtpGmailMailSender
    ) {
        this.mailSenders = Map.of(
                ProviderEnum.GMAIL, oAuthGmailMailSender,
                ProviderEnum.OUTLOOK, outlookSender,
                ProviderEnum.SMTP, smtpGmailMailSender
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
            case GMAIL -> oAuthGmailConfiguration(
                    javaMailSender,
                    (OAuthEmailMessageInformationDTO) emailMessageInformationDTO
            );
            case SMTP -> smtpConfiguration(
                    javaMailSender,
                    (SMTPEmailMessageInformationDTO) emailMessageInformationDTO
            );
            case OUTLOOK -> outlookConfiguration(
                    javaMailSender,
                    (SMTPEmailMessageInformationDTO) emailMessageInformationDTO
            );
            default -> throw new IllegalArgumentException("Invalid provider");
        }


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom(emailMessageInformationDTO.getFrom());
        helper.setTo(emailMessageInformationDTO.getToList().toArray(new String[0]));
        helper.setSubject(emailMessageInformationDTO.getSubject());
        helper.setText(emailMessageInformationDTO.getBody(), true);

        javaMailSender.send(mimeMessage);
    }

    private void oAuthGmailConfiguration(
            JavaMailSenderImpl mailSender,
            OAuthEmailMessageInformationDTO emailMessageInformationDTO
    ) {
        mailSender.setUsername(emailMessageInformationDTO.getFrom());
        mailSender.setPassword(null);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        props.put("mail.smtp.auth.xoauth2.accessToken", emailMessageInformationDTO.getToken());

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

    private void outlookConfiguration(
            JavaMailSenderImpl mailSender,
            SMTPEmailMessageInformationDTO emailMessageInformationDTO
    ) {
        mailSender.setUsername(emailMessageInformationDTO.getFrom());
        mailSender.setPassword(emailMessageInformationDTO.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
    }
}
