package com.ti9.send.email.core.domain.dto.account;

public record ProxySettings (
        String server,
        String port,
        String username,
        String password,
        String protocol
) {
}
