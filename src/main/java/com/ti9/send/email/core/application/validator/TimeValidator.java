package com.ti9.send.email.core.application.validator;

import com.ti9.send.email.core.infrastructure.validator.ValidTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TimeValidator implements ConstraintValidator<ValidTime, String> {

    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):[0-5][05]$"); // Formato HH:mm

    @Override
    public void initialize(ValidTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Deixe @NotNull lidar com nulos
        }

        // Verifica o formato do horário
        if (!TIME_PATTERN.matcher(value).matches()) {
            return false;
        }

        // Verifica se o horário é menor ou igual a 23:55
        String[] parts = value.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return !(hours == 23 && minutes > 55);
    }
}

