package com.ti9.send.email.core.domain.dto.account;

public record OauthSettings(
        String username,
        String password,
        String accessToken,
        String refreshToken
) {
}
