package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class SenderFacade {

    private final Map<ProviderEnum, Sender> senders;

    public SenderFacade(
            @Qualifier("senderSMTPEmailImpl") Sender smtpSender,
            @Qualifier("senderOutlookEmailImpl") Sender outlookSender,
            @Qualifier("senderGmailEmailImpl") Sender gmailSender
    ) {
        this.senders = Map.of(
                ProviderEnum.SMTP, smtpSender,
                ProviderEnum.OUTLOOK, outlookSender,
                ProviderEnum.GMAIL, gmailSender
        );
    }

    public void send(
            List<String> recipientList,
            List<String> ccRecipentList,
            List<String> bccRecipentList,
            String subject,
            String body,
            List<File> fileList,
            Account account
    ) {
        this.senders.get(account.getProvider()).send(
                recipientList,
                ccRecipentList,
                bccRecipentList,
                subject,
                body,
                fileList,
                account.getAccountSettings()
        );
    }

}
