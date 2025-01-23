package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.application.exceptions.ResourceNotFoundException;
import com.ti9.send.email.core.application.exceptions.messages.ExceptionMessages;
import com.ti9.send.email.core.application.mapper.message.MessageTemplateMapper;
import com.ti9.send.email.core.application.port.out.message.template.MessageTemplateRepository;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private final MessageTemplateRepository repository;
    private final MessageRuleService messageRuleService;

    public MessageTemplateServiceImpl(MessageTemplateRepository repository, MessageRuleService messageRuleService) {
        this.repository = repository;
        this.messageRuleService = messageRuleService;
    }

    @Override
    public DataWrapper<MessageTemplateDTO> createMessageTemplate(MessageTemplateRequest request) {

        messageRuleService.verifyIfRuleExists(request.messageRuleId());

        MessageTemplateDTO messageTemplateDTO = MessageTemplateMapper.toDTO(
                repository.save(MessageTemplateMapper.toEntity(request))
        );

        messageRuleService.setTemplateId(messageTemplateDTO.id(), request.messageRuleId());

        return new DataWrapper<>(messageTemplateDTO);
    }

    @Override
    public DataListWrapper<MessageTemplateDTO> listMessageTemplates() {
        return new DataListWrapper<>(repository.list().stream().map(MessageTemplateMapper::toDTO).toList());

    }

    @Override
    public DataWrapper<MessageTemplateDTO> getMessageTemplate(UUID uuid) {
        return new DataWrapper<>(MessageTemplateMapper.toDTO(
                repository.findById(uuid).orElseThrow(
                        () -> new ResourceNotFoundException(
                                ResourceNotFoundException.resourceMessage(
                                        ExceptionMessages.MESSAGE_TEMPLATE_NOT_FOUND.getMessage(),
                                        String.valueOf(uuid)
                                )
                        )
                )
        ));
    }

    @Override
    public DataWrapper<MessageTemplateDTO> updateMessageTemplate(UUID uuid, MessageTemplateRequest request) {
        Optional<MessageTemplate> messageOptional = repository.findById(uuid);
        if (messageOptional.isPresent()) {
            MessageTemplate messageTemplate = messageOptional.get();
            messageTemplate.update(request);
            return new DataWrapper<>(MessageTemplateMapper.toDTO(repository.save(messageTemplate)));
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundException.resourceMessage(
                            ExceptionMessages.MESSAGE_TEMPLATE_NOT_FOUND.getMessage(),
                            String.valueOf(uuid)
                    )
            );
        }
    }


    @Override
    public String formatBodyMessage(DocumentDTO documentDTO, String body) {

        Map<String, String> replacements = Map.of(
                "{{NOME_CLIENTE}}", documentDTO.name(),
                "{{NOME_CONTATO}}", documentDTO.billingContact(),
                "{{NUMERO_DOCUMENTO}}", documentDTO.document(),
                "{{DATA_EMISSAO}}", DateUtils.formatDateToString(documentDTO.issueDate()),
                "{{DATA_VENCIMENTO}}", DateUtils.formatDateToString(documentDTO.dueDate()),
                "{{DIAS_PARA_VENCIMENTO}}", String.valueOf(ChronoUnit.DAYS.between(documentDTO.dueDate(), LocalDate.now())),
                "{{VALOR_ABERTO}}", documentDTO.outstandingAmount().toString(),
                "{{VALOR_DOCUMENTO}}", documentDTO.documentAmount().toString()
        );
        return replacePlaceholders(body, replacements);
    }

    @Override
    public DataWrapper<MessageTemplateDTO> getMessageTemplateByRuleId(UUID ruleId) {
        MessageTemplate modelByRuleId = repository.findModelByRuleId(ruleId);

        if (Objects.nonNull(modelByRuleId)) {
            return new DataWrapper<>(MessageTemplateMapper.toDTO(modelByRuleId));
        } else {
            return new DataWrapper<>(null);
        }
    }


    private String replacePlaceholders(String body, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            body = body.replace(entry.getKey(), entry.getValue());
        }
        return body;
    }
}
