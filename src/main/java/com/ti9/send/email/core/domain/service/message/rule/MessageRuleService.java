package com.ti9.send.email.core.domain.service.message.rule;

import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleRequest;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;

import java.util.UUID;

public interface MessageRuleService {
    DataWrapper<MessageRuleDTO> createMessage(MessageRuleRequest request);

    DataListWrapper<SummaryMessageDTO> listMessages();

    DataWrapper<MessageRuleDTO> getMessage(UUID uuid);

    DataWrapper<MessageRuleDTO> updateMessage(UUID uuid, MessageRuleRequest request);

    void deleteMessage(UUID uuid);
}
