package com.ti9.send.email.core.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SmtpSettings.class, name = "SMTP"),
        @JsonSubTypes.Type(value = ProxySettings.class, name = "PROXY"),
        @JsonSubTypes.Type(value = OAuthSettings.class, name = "OAUTH")
})
@Getter
@Setter
public abstract class AccountSettings {

    private String type;

    protected AccountSettings(String type) {
        this.type = type;
    }
}