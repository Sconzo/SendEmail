package com.ti9.send.email.core.infrastructure.adapter.out.repository.message.template;

import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaMessageTemplateRepository extends JpaRepository<MessageTemplate, UUID> {
}
