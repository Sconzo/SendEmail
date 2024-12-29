package com.ti9.send.email.core.infrastructure.annotation;

import com.ti9.send.email.core.application.validator.TimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeValidator.class)
@Target({ ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTime {
    String message() default "Invalid time value. Allowed format: HH:mm, max value: 23:55";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
