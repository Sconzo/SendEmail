package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import jakarta.mail.MessagingException;

public interface Sender<T> {

    void send(T t) throws MessagingException;
}
