package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.application.exceptions.SendEmailException;
import com.ti9.send.email.core.application.exceptions.messages.ExceptionMessages;
import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.OAuthEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.SMTPEmailMessageInformationDTO;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
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

    @Autowired
    public SenderEmailImpl(
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
    ) {
        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) mailSenders.get(
                emailMessageInformationDTO.getProviderType()
        );
        switch (emailMessageInformationDTO.getProviderType()) {
            case GMAIL, OUTLOOK -> oAuthConfiguration(
                    javaMailSender,
                    (OAuthEmailMessageInformationDTO) emailMessageInformationDTO
            );
            case SMTP -> smtpConfiguration(
                    javaMailSender,
                    (SMTPEmailMessageInformationDTO) emailMessageInformationDTO
            );
            default -> throw new IllegalArgumentException(ExceptionMessages.ACCOUNT_PROVIDER_INVALID.getMessage());
        }


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
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
        } catch (Exception e) {
            throw new SendEmailException(ExceptionMessages.ERROR_SENDING_EMAIL.getMessage(), e);
        }
    }

    private void oAuthConfiguration(
            JavaMailSenderImpl mailSender,
            OAuthEmailMessageInformationDTO emailMessageInformationDTO
    ) {
        mailSender.setUsername(emailMessageInformationDTO.getFrom());
        mailSender.setPassword(null);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        props.put("mail.smtp.auth.xoauth2.accessToken", emailMessageInformationDTO.getOAuthSettings().getAccessToken());
        props.put("mail.smtp.user", emailMessageInformationDTO.getFrom());
        props.put("mail.debug", "true");

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
        final String username = emailMessageInformationDTO.getUsername();
        final String password = emailMessageInformationDTO.getPassword();
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        mailSender.setSession(session);
    }
}
