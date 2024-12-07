package com.ti9.send.email.core.domain.service.message.template;

import com.ti9.send.email.core.application.exceptions.InvalidInputException;
import com.ti9.send.email.core.application.mapper.message.MessageTemplateMapper;
import com.ti9.send.email.core.application.port.out.message.template.MessageTemplateRepository;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.document.PlaceholderDataDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import com.ti9.send.email.core.domain.service.document.DocumentService;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        viewFormattedMessage(uuid);
    }

    @Override
    public void updateMessageTemplate(UUID uuid) {

    }

    @Override
    public void deleteMessageTemplate(UUID uuid) {

    }

    @Override
    public String viewFormattedMessage(UUID templateId) {
        MessageTemplate messageTemplate = repository.findById(templateId)
                .orElseThrow(() -> new InvalidInputException("Message not found"));

        String body = messageTemplate.getBody();
        Map<UUID, String> bodyFormattedMappedByCreId = new HashMap<>();

        Set<String> docTypeSet = messageTemplate.getMessageRuleList().stream().map(MessageRule::getDocType).flatMap(Collection::stream).collect(Collectors.toSet());
        Set<PaymentStatusEnum> docStatusSet = messageTemplate.getMessageRuleList().stream().map(MessageRule::getDocStatus).flatMap(Collection::stream).collect(Collectors.toSet());

        List<PlaceholderDataDTO> placeholderDataDTOList = documentService.getDocumentPlaceholderFromDocType(
                docTypeSet,
                docStatusSet
        );

        Map<String, List<PlaceholderDataDTO>> placeHolderMappedByDocType = placeholderDataDTOList.stream()
                .collect(Collectors.groupingBy(PlaceholderDataDTO::docType));


        for (MessageRule messageRule : messageTemplate.getMessageRuleList()) {
            for (String docType : messageRule.getDocType()) {
                for (PlaceholderDataDTO placeholderDataDTO : placeHolderMappedByDocType.get(docType)) {
                    Map<String, String> replacements = Map.of(
                            "{{NOME_CLIENTE}}", placeholderDataDTO.name(),
                            "{{NOME_CONTATO}}", placeholderDataDTO.billingContact(),
                            "{{NUMERO_DOCUMENTO}}", placeholderDataDTO.document(),
                            "{{DATA_EMISSAO}}", DateUtils.formatDateToString(placeholderDataDTO.issueDate()),
                            "{{DATA_VENCIMENTO}}", DateUtils.formatDateToString(placeholderDataDTO.dueDate()),
                            "{{DIAS_PARA_VENCIMENTO}}", String.valueOf(ChronoUnit.DAYS.between(placeholderDataDTO.dueDate(), LocalDate.now())),
                            "{{VALOR_ABERTO}}", placeholderDataDTO.outstandingAmount().toString(),
                            "{{VALOR_DOCUMENTO}}", placeholderDataDTO.documentAmount().toString()
                    );
                    String bodyFormatted = replacePlaceholders(body, replacements);
                    bodyFormattedMappedByCreId.put(placeholderDataDTO.creId(), bodyFormatted);
                }

            }
        }



        return bodyFormattedMappedByCreId.get(templateId);
    }


    private String replacePlaceholders(String body, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            body = body.replace(entry.getKey(), entry.getValue());
        }
        return body;
    }
}
