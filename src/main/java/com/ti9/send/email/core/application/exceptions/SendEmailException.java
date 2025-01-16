package com.ti9.send.email.core.application.exceptions;

import java.io.Serial;

public class SendEmailException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
