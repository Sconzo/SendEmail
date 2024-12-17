package com.ti9.send.email.core.application.port.out.message.template;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;

import java.util.Optional;
import java.util.UUID;

public interface MessageTemplateRepository extends GenericRepository<MessageTemplate, UUID> {
    MessageTemplate findModelByRuleId(UUID ruleId);
}
