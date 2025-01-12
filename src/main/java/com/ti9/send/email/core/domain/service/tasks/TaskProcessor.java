package com.ti9.send.email.core.domain.service.tasks;

@FunctionalInterface
public interface TaskProcessor<T> {
    void process(T item) throws Exception;
}
