package com.ti9.send.email.core.domain.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProxySettings extends AccountSettings {
    private String server;
    private String port;
    private String username;
    private String password;
    private String protocol;

    public ProxySettings() {
        super("PROXY");
    }
}
