package com.ti9.send.email.core.domain.dto.message.rule;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MessageRuleRequest(
        @NotEmpty(message = "Name cannot be empty") String name,
        @NotNull(message = "dateRule cannot be null") DateRuleEnum dateRule,
        @NotNull(message = "dateBase cannot be null") BaseDateEnum dateBase,
        @Valid @Size(min = 1) List<@Pattern(regexp = "^\\d{2}:\\d[05]$", message = "Each sending time must be in the format HH:MM where MM ends with 0 or 5") String> sendingTimeList,
        @NotEmpty(message = "sendingDayList cannot be empty") List<Short> sendingDayList,
        @NotEmpty(message = "docTypeList cannot be empty") List<String> docTypeList,
        @NotEmpty(message = "paymentStatusList cannot be empty") List<PaymentStatusEnum> paymentStatusList,
        @NotNull(message = "status cannot be null") StatusEnum status,
        @NotNull(message = "sendAttachments cannot be null") Boolean sendAttachments
) {
}
