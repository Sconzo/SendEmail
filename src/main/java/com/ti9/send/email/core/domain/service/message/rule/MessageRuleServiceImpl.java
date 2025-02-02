package com.ti9.send.email.core.domain.service.message.rule;

import com.ti9.send.email.core.application.exceptions.ResourceNotFoundException;
import com.ti9.send.email.core.application.exceptions.messages.ExceptionMessages;
import com.ti9.send.email.core.application.mapper.message.MessageRuleMapper;
import com.ti9.send.email.core.application.port.out.message.rule.MessageRuleRepository;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleRequest;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.infrastructure.adapter.out.repository.message.rule.JpaMessageRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageRuleServiceImpl implements MessageRuleService {

    private final MessageRuleRepository repository;

    public MessageRuleServiceImpl(MessageRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataWrapper<MessageRuleDTO> createMessage(MessageRuleRequest request) {
        return new DataWrapper<>(MessageRuleMapper.toDTO(repository.save(MessageRuleMapper.toEntity(request))));
    }

    @Override
    public DataListWrapper<SummaryMessageDTO> listMessages() {
        return new DataListWrapper<>(repository.list().stream().map(MessageRuleMapper::toSummary).toList());
    }

    @Override
    public DataWrapper<MessageRuleDTO> getMessage(UUID uuid) {
        Optional<MessageRule> messageOptional = repository.findById(uuid);
        if (messageOptional.isPresent()) {
            return new DataWrapper<>(MessageRuleMapper.toDTO(messageOptional.get()));
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundException.resourceMessage(
                            ExceptionMessages.MESSAGE_RULE_NOT_FOUND.getMessage(),
                            String.valueOf(uuid)
                    )
            );
        }
    }

    @Override
    public DataWrapper<MessageRuleDTO> updateMessage(UUID uuid, MessageRuleRequest request) {
        Optional<MessageRule> messageOptional = repository.findById(uuid);
        if (messageOptional.isPresent()) {
            MessageRule messageRule = messageOptional.get();
            messageRule.update(request);
            return new DataWrapper<>(MessageRuleMapper.toDTO(repository.save(messageRule)));
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundException.resourceMessage(
                            ExceptionMessages.MESSAGE_RULE_NOT_FOUND.getMessage(),
                            String.valueOf(uuid)
                    )
            );
        }
    }

    @Override
    public void deleteMessage(UUID uuid) {
        repository.deleteById(uuid);
    }

    @Override
    public List<MessageRule> getActiveRules(String currentHourMinute) {
        return repository.findActiveTemplates(currentHourMinute);
    }

    @Override
    public void setTemplateId(UUID templateId, UUID messageRuleId) {
        repository.setTemplateId(templateId, messageRuleId);
    }

    @Override
    public void changeStatus(UUID uuid) {
        repository.changeStatus(uuid);
    }

    @Override
    public void verifyIfRuleExists(UUID uuid) {
        Optional<MessageRule> messageRuleOptional = repository.findById(uuid);

        if (messageRuleOptional.isEmpty()) {
            throw new ResourceNotFoundException(
                    ResourceNotFoundException.resourceMessage(
                            ExceptionMessages.MESSAGE_RULE_NOT_FOUND.getMessage(),
                            String.valueOf(uuid)
                    )
            );
        }
    }
}
