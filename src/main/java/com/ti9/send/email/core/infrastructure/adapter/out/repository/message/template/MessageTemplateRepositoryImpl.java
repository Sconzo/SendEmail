package com.ti9.send.email.core.infrastructure.adapter.out.repository.message.template;

import com.ti9.send.email.core.application.port.out.message.template.MessageTemplateRepository;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MessageTemplateRepositoryImpl implements MessageTemplateRepository {

    private final JpaMessageTemplateRepository jpaRepository;

    public MessageTemplateRepositoryImpl(JpaMessageTemplateRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }


    @Override
    public Optional<MessageTemplate> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public MessageTemplate save(MessageTemplate messageTemplate) {
        return jpaRepository.save(messageTemplate);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<MessageTemplate> list() {
        return jpaRepository.findAll();
    }
}
