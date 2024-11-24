package com.ti9.send.email.core.infrastructure.adapter.converter;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class PaymentStatusEnumArrayConverter implements AttributeConverter<List<PaymentStatusEnum>, String> {
    @Override
    public String convertToDatabaseColumn(List<PaymentStatusEnum> strings) {
        if (strings == null || strings.isEmpty()) {
            return null;
        }
        return strings.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<PaymentStatusEnum> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .map(PaymentStatusEnum::valueOf)
                .collect(Collectors.toList());
    }
}
