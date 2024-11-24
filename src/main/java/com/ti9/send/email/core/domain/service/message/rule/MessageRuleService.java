package com.ti9.send.email.core.domain.service.message.rule;

import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.dto.message.MessageDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;

import java.util.UUID;

public interface MessageRuleService {
    DataWrapper<MessageDTO> createMessage(MessageRequest request);

    DataListWrapper<SummaryMessageDTO> listMessages();

    DataWrapper<MessageDTO> getMessage(UUID uuid);

    DataWrapper<MessageDTO> updateMessage(UUID uuid, MessageRequest request);

    void deleteMessage(UUID uuid);
}
