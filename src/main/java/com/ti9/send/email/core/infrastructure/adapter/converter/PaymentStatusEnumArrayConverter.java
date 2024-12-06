package com.ti9.send.email.core.infrastructure.adapter.converter;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class PaymentStatusEnumArrayConverter implements AttributeConverter<List<PaymentStatusEnum>, String[]> {
    @Override
    public String[] convertToDatabaseColumn(List<PaymentStatusEnum> paymentStatusEnumList) {
        if (paymentStatusEnumList == null || paymentStatusEnumList.isEmpty()) {
            return null;
        }
        return paymentStatusEnumList.stream().map(Enum::name).toArray(String[]::new);
    }

    @Override
    public List<PaymentStatusEnum> convertToEntityAttribute(String[] dbData) {
        if (dbData == null || dbData.length == 0) {
            return null;
        }
        return Arrays.stream(dbData)
                .map(PaymentStatusEnum::valueOf)
                .collect(Collectors.toList());
    }
}
