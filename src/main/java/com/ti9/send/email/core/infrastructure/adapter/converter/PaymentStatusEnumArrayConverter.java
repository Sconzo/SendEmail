package com.ti9.send.email.core.infrastructure.adapter.converter;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class PaymentStatusEnumArrayConverter implements AttributeConverter<List<PaymentStatusEnum>, String> {
    @Override
    public String convertToDatabaseColumn(List<PaymentStatusEnum> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(PaymentStatusEnum::name)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
    }

    @Override
    public List<PaymentStatusEnum> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(","))
                .map(PaymentStatusEnum::valueOf)
                .toList();
    }
}
