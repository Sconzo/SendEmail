package com.ti9.send.email.core.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti9.send.email.core.infrastructure.adapter.utils.EncryptUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthSettings extends AccountSettings {
    private String accessToken;
    private String refreshToken;

    @JsonCreator
    public OAuthSettings(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken
    ) {
        super("OAUTH");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public OAuthSettings() {
        super("OAUTH");
    }
}
