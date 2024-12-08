package com.ti9.send.email.core.domain.model.message.template.enums;

import lombok.Getter;

public enum ActionEnum {
    EMAIL("Enviar por E-mail");

    @Getter
    private String description;

    ActionEnum(String description) {
        this.description = description;
    }
}
