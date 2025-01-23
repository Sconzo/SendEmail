package com.ti9.send.email.core.application.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessages {
    ACCOUNT_NOT_FOUND("Account not found."),
    ACCOUNT_SETTINGS_JSON_INVALID_FOR_OAUTH_SETTINGS("Account settings json invalid for OAuth Settings."),
    ACCOUNT_SETTINGS_INFORMATION_CANNOT_BE_NULL("Account settings information cannot be null."),
    ACCOUNT_PROVIDER_INVALID("Account provider invalid."),
    MESSAGE_RULE_NOT_FOUND("Message rule not found."),
    MESSAGE_TEMPLATE_NOT_FOUND("Message template not found."),

    ACCESS_TOKEN_VERIFICATION_ERROR("Access token inválid or expired."),
    ERROR_RENEWING_ACCESS_TOKEN_USING_REFRESH_TOKEN("Error renewing access token using refresh token."),
    MICROSOFT_API_ERROR("Error when trying to request microsoft api."),
    GOOGLE_API_ERROR("Error when trying to request google api."),
    ERROR_SENDING_EMAIL("Error sending email")
    ;

    private final String message;
}
