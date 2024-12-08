package com.ti9.send.email.core.infrastructure.adapter.in.scheduler;

import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import com.ti9.send.email.core.domain.service.document.DocumentService;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import com.ti9.send.email.core.domain.service.message.template.MessageTemplateService;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class EmailScheduler {

    private final MessageRuleService messageRuleService;
    private final MessageTemplateService messageTemplateService;
    private final DocumentService documentService;

    public EmailScheduler(MessageRuleService messageRuleService, MessageTemplateService messageTemplateService, DocumentService documentService) {
        this.messageRuleService = messageRuleService;
        this.messageTemplateService = messageTemplateService;
        this.documentService = documentService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendEmail() {
        System.out.println("Tarefa executada: " + System.currentTimeMillis());
        LocalTime currentTime = LocalTime.now();
        String currentHourMinute = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        List<MessageRule> messageRuleList = messageRuleService.getActiveRules(currentHourMinute);

        for (MessageRule messageRule : messageRuleList) {
            DateRuleEnum dateRule = messageRule.getDateRule();

            List<DocumentDTO> documentDTOList = documentService.getDocumentPlaceholderFromDocType(
                    messageRule.getDocType(),
                    messageRule.getDocStatus()
            );

            for (DocumentDTO document : documentDTOList) {
                if (Objects.equals(messageRule.getDateIndex(), BaseDateEnum.EMISSAO)) {
                    if (Objects.equals(dateRule, DateRuleEnum.DIAS_UTEIS)) {
                        if (messageRule.getSelectedDay().stream()
                                .map(Short::intValue)
                                .toList()
                                .contains(DateUtils.calculateWorkDaysDifference(
                                        LocalDate.now(),
                                        document.issueDate(),
                                        DateUtils.getBrazilianHolidays())
                                )
                        ){
                            shouldSendEmail(messageRule, document);
                        }
                    } else if (Objects.equals(dateRule, DateRuleEnum.DIAS_CORRIDOS)) {
                        if (messageRule.getSelectedDay().contains(document.calendarDayDifferenceIssueDate())){
                            shouldSendEmail(messageRule, document);
                        }
                    }

                } else if (Objects.equals(messageRule.getDateIndex(), BaseDateEnum.VENCIMENTO)) {
                    if (Objects.equals(dateRule, DateRuleEnum.DIAS_UTEIS)) {
                        if (messageRule.getSelectedDay().stream()
                                .map(Short::intValue)
                                .toList()
                                .contains(DateUtils.calculateWorkDaysDifference(
                                        LocalDate.now(),
                                        document.dueDate(),
                                        DateUtils.getBrazilianHolidays())
                                )
                        ){
                            shouldSendEmail(messageRule, document);
                        }
                    } else if (Objects.equals(dateRule, DateRuleEnum.DIAS_CORRIDOS)) {
                        if (messageRule.getSelectedDay().contains(document.calendarDayDifferenceIssueDate())){
                            shouldSendEmail(messageRule, document);
                        }
                    }
                }
            }
        }
        System.out.println("teste");
    }

    private void shouldSendEmail(MessageRule messageRule, DocumentDTO document) {
        String formatBodyMessage = messageTemplateService.formatBodyMessage(document, messageRule.getMessageTemplate().getBody());
        System.out.println("Deve mandar email -> " + formatBodyMessage);
    }
}

