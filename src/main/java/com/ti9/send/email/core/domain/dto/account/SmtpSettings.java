package com.ti9.send.email.core.domain.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti9.send.email.core.infrastructure.adapter.utils.EncryptUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmtpSettings extends AccountSettings {

    private String host;
    private int port;
    private String username;
    private String password;
    private boolean requiredTLS;
    private String encryptionMethod;
    private boolean secureAuthentication;

    @JsonCreator
    public SmtpSettings(
            @JsonProperty("host") String host,
            @JsonProperty("port") int port,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty(value = "should_encrypt", defaultValue = "true") boolean shouldEncrypt
    ) {
        super("SMTP");
        this.username = username;
        this.password = shouldEncrypt ? EncryptUtils.encrypt(password) : password;
        this.host = host;
        this.port = port;
    }

    public SmtpSettings() {
        super("SMTP");
    }

    public void decryptPassword() {
        this.password = EncryptUtils.decrypt(this.password);
    }

}
