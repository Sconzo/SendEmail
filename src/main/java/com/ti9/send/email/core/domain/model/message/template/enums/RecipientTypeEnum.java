package com.ti9.send.email.core.domain.model.message.template.enums;

import lombok.Getter;

public enum RecipientTypeEnum {
    COBRANCA("Contato da Cobrança");

    @Getter
    private String description;

    RecipientTypeEnum(String description) {
        this.description = description;
    }
}
