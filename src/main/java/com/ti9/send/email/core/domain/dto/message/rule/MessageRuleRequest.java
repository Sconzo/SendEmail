package com.ti9.send.email.core.domain.dto.message.rule;

import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;

import java.util.List;

public record MessageRuleRequest(
        String name,
        DateRuleEnum dateRule,
        BaseDateEnum dateBase,
        List<String> sendingTimeList,
        List<Short> sendingDayList,
        List<String> docTypeList,
        List<PaymentStatusEnum> paymentStatusList,
        StatusEnum status,
        Boolean sendAttachments
) {
}
