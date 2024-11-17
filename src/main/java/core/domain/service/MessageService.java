package core.domain.service;

import core.domain.dto.message.CreateMessageRequest;

import java.util.UUID;

public interface MessageService {
    void createMessage(CreateMessageRequest request);

    void listMessages();

    void getMessage(UUID uuid);

    void updateMessage(UUID uuid);

    void deleteMessage(UUID uuid);
}
