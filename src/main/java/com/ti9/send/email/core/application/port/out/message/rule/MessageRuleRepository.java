package com.ti9.send.email.core.application.port.out.message.rule;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRuleRepository extends GenericRepository<MessageRule, UUID> {
    List<MessageRule> findActiveTemplates(String currentHourMinute);
}
