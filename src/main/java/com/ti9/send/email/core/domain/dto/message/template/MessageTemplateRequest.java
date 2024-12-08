package com.ti9.send.email.core.domain.dto.message.template;

import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;

import java.util.List;
import java.util.UUID;

public record MessageTemplateRequest(
        UUID messageRuleId,
        ActionEnum action,
        UUID senderId,
        RecipientTypeEnum recipientType,
        String replyTO,
        //String otherRecipients,
        List<String> cc,
        List<String> cco,
        String subject,
        String body
) {
}
