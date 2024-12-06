package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.application.mapper.MessageTemplateMapper;
import com.ti9.send.email.core.application.port.out.message.template.MessageTemplateRepository;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService{

    private final MessageTemplateRepository repository;

    public MessageTemplateServiceImpl(MessageTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataWrapper<MessageTemplateDTO> createMessageTemplate(MessageTemplateRequest request) {

        return new DataWrapper<>(MessageTemplateMapper.toDTO(repository.save(MessageTemplateMapper.toEntity(request))));
    }

    @Override
    public void listMessageTemplates() {

    }

    @Override
    public void getMessageTemplate(UUID uuid) {

    }

    @Override
    public void updateMessageTemplate(UUID uuid) {

    }

    @Override
    public void deleteMessageTemplate(UUID uuid) {

    }
}
