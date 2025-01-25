package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import com.ti9.send.email.core.domain.dto.account.SmtpSettings;

import java.io.File;
import java.util.List;

public interface Sender {

    void send(
            List<String> recipientList,
            List<String> ccRecipentList,
            List<String> bccRecipentList,
            String subject,
            String body,
            List<File> fileList,
            AccountSettings accountSettings
    );
}
