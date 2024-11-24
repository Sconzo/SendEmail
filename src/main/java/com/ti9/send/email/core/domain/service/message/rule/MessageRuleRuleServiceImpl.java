package com.ti9.send.email.core.domain.service.message.rule;

import com.ti9.send.email.core.application.exceptions.ResourceNotFoundException;
import com.ti9.send.email.core.application.mapper.MessageMapper;
import com.ti9.send.email.core.application.port.out.message.MessageRepository;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.dto.message.MessageDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.model.message.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageRuleRuleServiceImpl implements MessageRuleService {

    private final MessageRepository repository;

    public MessageRuleRuleServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataWrapper<MessageDTO> createMessage(MessageRequest request) {
        return new DataWrapper<>(MessageMapper.toResponse(repository.save(MessageMapper.toEntity(request))));
    }

    @Override
    public DataListWrapper<SummaryMessageDTO> listMessages() {
        return new DataListWrapper<>(repository.list().stream().map(MessageMapper::toSummary).toList());
    }

    @Override
    public DataWrapper<MessageDTO> getMessage(UUID uuid) {
        Optional<Message> messageOptional = repository.findById(uuid);
        if (messageOptional.isPresent()) {
            return new DataWrapper<>(MessageMapper.toResponse(messageOptional.get()));
        } else {
            throw new ResourceNotFoundException("Message with id " + uuid + " not found.");
        }
    }

    @Override
    public DataWrapper<MessageDTO> updateMessage(UUID uuid, MessageRequest request) {
        Optional<Message> messageOptional = repository.findById(uuid);
        if (messageOptional.isPresent()) {
            messageOptional.get().update(request);
            return new DataWrapper<>(MessageMapper.toResponse(repository.save(messageOptional.get())));
        } else {
            throw new ResourceNotFoundException("Message with id " + uuid + " not found.");
        }
    }

    @Override
    public void deleteMessage(UUID uuid) {
        repository.deleteById(uuid);
    }
}
