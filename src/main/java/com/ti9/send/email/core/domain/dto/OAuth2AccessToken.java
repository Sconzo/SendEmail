package com.ti9.send.email.core.domain.dto;

import java.time.Instant;

public class OAuth2AccessToken {
    private final String tokenValue;
    private final String refreshToken;
    private final Instant expiresAt;

    public OAuth2AccessToken(String tokenValue, String refreshToken, Instant expiresAt) {
        this.tokenValue = tokenValue;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

}
