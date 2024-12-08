package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.application.mapper.message.MessageTemplateMapper;
import com.ti9.send.email.core.application.port.out.message.template.MessageTemplateRepository;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.service.document.DocumentService;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService{

    private final MessageTemplateRepository repository;
    private final DocumentService documentService;

    public MessageTemplateServiceImpl(MessageTemplateRepository repository, DocumentService documentService) {
        this.repository = repository;
        this.documentService = documentService;
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


    private String replacePlaceholders(String body, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            body = body.replace(entry.getKey(), entry.getValue());
        }
        return body;
    }
}
