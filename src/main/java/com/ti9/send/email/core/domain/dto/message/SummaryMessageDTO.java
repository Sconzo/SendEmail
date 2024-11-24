package com.ti9.send.email.core.domain.dto.message;

import com.ti9.send.email.core.domain.model.enums.StatusEnum;

import java.util.UUID;

public record SummaryMessageDTO(
        UUID id,
        String name,
        StatusEnum status
) {
}
