package com.ti9.send.email.core.domain.dto.message;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;

import java.util.List;

public record CreateMessageRequest(
        String name,
        DateRuleEnum dateRule,
        BaseDateEnum dateBase,
        List<String> sendingTimeList,
        List<Long> sendingDayList,
        List<PaymentStatusEnum> paymentStatusList,
        Boolean sendAttachments
) {
}
