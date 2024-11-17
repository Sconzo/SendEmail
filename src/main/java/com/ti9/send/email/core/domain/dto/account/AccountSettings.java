package com.ti9.send.email.core.domain.dto.account;

public record AccountSettings(
        ProxySettings proxySettings,
        SmtpSettings smtpSettings,
        OauthSettings oauthSettings
) {
}