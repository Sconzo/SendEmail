package com.ti9.send.email.core.domain.dto.message.template;

import com.ti9.send.email.core.domain.model.message.template.enums.ActionEnum;
import com.ti9.send.email.core.domain.model.message.template.enums.RecipientTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record MessageTemplateRequest(
        @NotNull(message = "messageRuleId cannot be null") UUID messageRuleId,
        @NotNull(message = "action cannot be null") ActionEnum action,
        @NotNull(message = "senderId cannot be null") UUID senderId,
        @NotNull(message = "recipientType cannot be null") RecipientTypeEnum recipientType,
        String replyTo,
        List<String> cc,
        List<String> cco,
        @NotBlank(message = "subject cannot be blank") String subject,
        @NotBlank(message = "body cannot be blank") String body
) {
}
