package com.ti9.send.email.core.infrastructure.config;

import com.google.api.services.gmail.Gmail;
import com.ti9.send.email.core.infrastructure.adapter.in.controller.EmailService;
import com.ti9.send.email.core.infrastructure.adapter.in.controller.GmailServiceInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public Gmail gmailService() throws IOException {
        return GmailServiceInitializer.getGmailService();
    }

    @Bean
    public EmailService emailService(Gmail gmailService) {
        return new EmailService(gmailService);
    }
}
