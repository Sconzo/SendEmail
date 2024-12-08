package com.ti9.send.email.core.domain.dto;

import java.util.UUID;

public record BaseDTO(
        UUID id,
        String value
) {
}
