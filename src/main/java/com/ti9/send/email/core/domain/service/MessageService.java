package com.ti9.send.email.core.domain.service;

import com.ti9.send.email.core.domain.dto.message.CreateMessageRequest;

import java.util.UUID;

public interface MessageService {
    void createMessage(CreateMessageRequest request);

    void listMessages();

    void getMessage(UUID uuid);

    void updateMessage(UUID uuid);

    void deleteMessage(UUID uuid);
}
