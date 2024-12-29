package com.ti9.send.email.core.infrastructure.adapter.utils;

import com.google.gson.Gson;

import java.util.Objects;

public class ConverterUtils {

    public static String serializeFirstNonNull(Gson gson, Object... objects) {
        for (Object obj : objects) {
            if (Objects.nonNull(obj)) {
                return gson.toJson(obj);
            }
        }
        return null;
    }
}
