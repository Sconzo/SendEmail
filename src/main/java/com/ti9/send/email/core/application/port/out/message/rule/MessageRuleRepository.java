package com.ti9.send.email.core.application.port.out.message.rule;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.message.MessageRule;

import java.util.UUID;

public interface MessageRuleRepository extends GenericRepository<MessageRule, UUID> {
}
