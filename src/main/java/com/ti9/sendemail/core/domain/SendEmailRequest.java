package com.ti9.sendemail.core.domain;

import java.util.List;

public record SendEmailRequest(
        List<String> recipients,
        String subject,
        String template
) {
}
