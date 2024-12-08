package com.ti9.send.email.core.infrastructure.adapter.converter;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringArrayConverter implements AttributeConverter<List<String>, String[]> {
    @Override
    public String[] convertToDatabaseColumn(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return null;
        }
        return strings.stream().map(String::trim).toArray(String[]::new);
    }

    @Override
    public List<String> convertToEntityAttribute(String[] s) {
        if (s == null || s.length == 0) {
            return null;
        }
        return Arrays.stream(s).collect(Collectors.toList());
    }
}
