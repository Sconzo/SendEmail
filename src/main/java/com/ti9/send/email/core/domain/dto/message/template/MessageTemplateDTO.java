package com.ti9.send.email.core.domain.dto.message.template;

import com.ti9.send.email.core.domain.dto.BaseDTO;

import java.util.List;
import java.util.UUID;

public record MessageTemplateDTO(
        UUID id,
        String action,
        BaseDTO senderAccount,
        String recipient,
        String replyTo,
        List<String> cc,
        List<String> cco,
        String subject,
        String body
) {}
