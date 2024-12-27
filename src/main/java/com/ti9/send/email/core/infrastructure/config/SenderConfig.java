package com.ti9.send.email.core.infrastructure.config;

import com.ti9.send.email.core.infrastructure.adapter.out.sender.Sender;
import com.ti9.send.email.core.infrastructure.adapter.out.sender.SenderEmailImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SenderConfig {

    @Bean
    public List<Sender<?>> senders(SenderEmailImpl senderEmailImpl) {
        return List.of(senderEmailImpl);
    }
}
