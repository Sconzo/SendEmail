package com.ti9.send.email.core.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthSettings extends AccountSettings {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private int expiresIn;

    @JsonCreator
    public OAuthSettings(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("scope") String scope,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") int expiresIn
    ) {
        super("OAUTH");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public void update(OAuthSettings request) {
        this.accessToken = request.getAccessToken();
        this.refreshToken = request.getRefreshToken();
        this.scope = request.getScope();
        this.tokenType = request.getTokenType();
        this.expiresIn = request.getExpiresIn();
    }
}
