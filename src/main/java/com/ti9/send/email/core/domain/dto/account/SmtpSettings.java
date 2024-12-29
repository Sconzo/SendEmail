package com.ti9.send.email.core.domain.dto.account;

import com.ti9.send.email.core.infrastructure.adapter.utils.EncryptUtils;

public record SmtpSettings(
        String username,
        String password,
        String host,
        String port
) {
    public SmtpSettings(String username, String password, String host, String port) {
        this.username = username;
        this.password = EncryptUtils.encrypt(password);
        this.host = host;
        this.port = port;
    }
}
