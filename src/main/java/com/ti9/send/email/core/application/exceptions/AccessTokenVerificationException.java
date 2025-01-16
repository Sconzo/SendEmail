package com.ti9.send.email.core.application.exceptions;

import java.io.Serial;

public class AccessTokenVerificationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AccessTokenVerificationException(String message) {
        super(message);
    }

    public AccessTokenVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
