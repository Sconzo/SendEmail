package com.ti9.send.email.core.domain.service.message.rule;

import com.ti9.send.email.core.domain.dto.message.CreateMessageRequest;

import java.util.UUID;

public interface MessageRuleService {
    void createMessage(CreateMessageRequest request);

    void listMessages();

    void getMessage(UUID uuid);

    void updateMessage(UUID uuid);

    void deleteMessage(UUID uuid);
}
