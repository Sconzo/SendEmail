package core.domain.dto.message;

import core.domain.model.enums.PaymentStatusEnum;
import core.domain.model.message.enums.BaseDateEnum;
import core.domain.model.message.enums.DateRuleEnum;

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
