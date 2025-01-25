package com.ti9.send.email.core.infrastructure.config;

import com.ti9.send.email.core.infrastructure.adapter.out.sender.Sender;
import com.ti9.send.email.core.infrastructure.adapter.out.sender.SenderOutlookEmailImpl;
import com.ti9.send.email.core.infrastructure.adapter.out.sender.SenderSMTPEmailImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

    @Bean(name = "senderSMTPEmailImpl")
    public Sender senderSMTPEmailImpl(SenderSMTPEmailImpl senderSMTPEmailImpl) {
        return senderSMTPEmailImpl;
    }

    @Bean(name = "senderOutlookEmailImpl")
    public Sender senderOutlookEmail(SenderOutlookEmailImpl senderOutlookEmailImpl) {
        return senderOutlookEmailImpl;
    }
}
