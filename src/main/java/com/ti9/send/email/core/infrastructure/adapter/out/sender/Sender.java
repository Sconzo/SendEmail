package com.ti9.send.email.core.infrastructure.adapter.out.sender;

public interface Sender<T> {

    void send(T t);
}
