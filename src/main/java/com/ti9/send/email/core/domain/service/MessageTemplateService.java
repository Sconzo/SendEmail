package com.ti9.send.email.core.domain.service;

import com.ti9.send.email.core.domain.dto.message.template.CreateMessageTemplateRequest;

import java.util.UUID;

public interface MessageTemplateService {
    void createMessageTemplate(CreateMessageTemplateRequest request);

    void listMessageTemplates();

    void getMessageTemplate(UUID uuid);

    void updateMessageTemplate(UUID uuid);

    void deleteMessageTemplate(UUID uuid);
}
