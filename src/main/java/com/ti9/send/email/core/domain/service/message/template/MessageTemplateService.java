package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;

import java.util.UUID;

public interface MessageTemplateService {
    DataWrapper<MessageTemplateDTO> createMessageTemplate(MessageTemplateRequest request);

    void listMessageTemplates();

    void getMessageTemplate(UUID uuid);

    void updateMessageTemplate(UUID uuid);

    void deleteMessageTemplate(UUID uuid);

    String formatBodyMessage(DocumentDTO documentDTO, String body);
}
