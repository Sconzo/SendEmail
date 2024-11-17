package com.ti9.send.email.mock.domain;

import java.util.List;

public record SendEmailRequest(
        List<String> recipients,
        String subject,
        String template
) {
}
