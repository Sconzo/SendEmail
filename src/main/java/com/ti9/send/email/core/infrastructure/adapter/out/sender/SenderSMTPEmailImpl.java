package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.application.exceptions.SendEmailException;
import com.ti9.send.email.core.application.exceptions.messages.ExceptionMessages;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import com.ti9.send.email.core.domain.dto.account.SmtpSettings;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Component
public class SenderSMTPEmailImpl implements Sender {

    private final JavaMailSender mailSender;
    @Value("${email}")
    private String from;

    @Autowired
    public SenderSMTPEmailImpl(
            JavaMailSender javaMailSender
    ) {
        mailSender = javaMailSender;
    }

    @Override
    public void send(
            List<String> recipientList,
            List<String> ccRecipentList,
            List<String> bccRecipentList,
            String subject,
            String body,
            List<File> fileList,
            AccountSettings accountSettings
    ) {
        try {
            smtpConfiguration((JavaMailSenderImpl) mailSender, (SmtpSettings) accountSettings);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(recipientList.toArray(new String[0]));
            helper.setCc(ccRecipentList.toArray(new String[0]));
            helper.setBcc(bccRecipentList.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, true);
            for (File file : fileList) {
                helper.addAttachment(file.getName(), file);
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException(ExceptionMessages.ERROR_SENDING_EMAIL.getMessage(), e);
        }
    }

    private void smtpConfiguration(
            JavaMailSenderImpl mailSender,
            SmtpSettings smtpSettings
    ) {
        mailSender.setUsername(smtpSettings.getUsername());
        smtpSettings.decryptPassword();
        mailSender.setPassword(smtpSettings.getPassword());

        mailSender.setHost("smtp.gmail.com"); //mudar dependendo do username
        mailSender.setPort(587);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //mudar dependendo do username
    }
}
