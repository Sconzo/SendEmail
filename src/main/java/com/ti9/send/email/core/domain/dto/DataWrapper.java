package com.ti9.send.email.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataWrapper<T> {
    private int status;
    private String message;
    private T data;

    public DataWrapper(T data) {
        this.data = data;
    }
}
