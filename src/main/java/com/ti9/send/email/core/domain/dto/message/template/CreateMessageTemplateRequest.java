package com.ti9.send.email.core.domain.dto.message.template;

import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;

import java.util.List;
import java.util.UUID;

public record CreateMessageTemplateRequest(
        ActionEnum action,
        UUID senderId,
        RecipientTypeEnum recipientType,
        String otherRecipients,
        String answerTo,
        List<String> accountsInCopy,
        List<String> blindAccountsInCopy,
        String subject,
        String messageContent
) {
}
