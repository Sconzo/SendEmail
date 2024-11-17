package core.domain.dto.account;

public record AccountSettings(
        ProxySettings proxySettings,
        SmtpSettings smtpSettings,
        OauthSettings oauthSettings
) {
}