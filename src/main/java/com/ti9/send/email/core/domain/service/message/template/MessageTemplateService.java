package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;

import java.util.UUID;

public interface MessageTemplateService {
    DataWrapper<MessageTemplateDTO> createMessageTemplate(MessageTemplateRequest request);

    DataListWrapper<MessageTemplateDTO> listMessageTemplates();

    DataWrapper<MessageTemplateDTO> getMessageTemplate(UUID uuid);

    DataWrapper<MessageTemplateDTO> updateMessageTemplate(UUID uuid, MessageTemplateRequest request);

    String formatBodyMessage(DocumentDTO documentDTO, String body);
}
