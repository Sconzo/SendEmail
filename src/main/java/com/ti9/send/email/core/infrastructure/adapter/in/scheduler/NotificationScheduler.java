package com.ti9.send.email.core.infrastructure.adapter.in.scheduler;

import com.ti9.send.email.core.application.mapper.message.EmailMessageInformationMapper;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.information.MessageInformationDTO;
import com.ti9.send.email.core.domain.model.attach.Attach;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import com.ti9.send.email.core.domain.model.inbox.InboxLink;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import com.ti9.send.email.core.domain.service.attach.AttachServiceImpl;
import com.ti9.send.email.core.domain.service.document.DocumentService;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import com.ti9.send.email.core.domain.service.message.template.MessageTemplateService;
import com.ti9.send.email.core.infrastructure.adapter.out.sender.Sender;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class NotificationScheduler {

    private final MessageRuleService messageRuleService;
    private final MessageTemplateService messageTemplateService;
    private final DocumentService documentService;
    private final AttachServiceImpl attachServiceImpl;
    private final List<Sender<MessageInformationDTO>> senderList;

    public NotificationScheduler(
            MessageRuleService messageRuleService,
            MessageTemplateService messageTemplateService,
            DocumentService documentService,
            List<Sender<MessageInformationDTO>> senderList,
            AttachServiceImpl attachServiceImpl
    ) {
        this.messageRuleService = messageRuleService;
        this.messageTemplateService = messageTemplateService;
        this.documentService = documentService;
        this.attachServiceImpl = attachServiceImpl;
        this.senderList = senderList;
    }

    @Scheduled(cron = "0 * * * * *")
    public void processMessages() {
        System.out.println("Tarefa executada: " + System.currentTimeMillis());
        LocalTime currentTime = LocalTime.now();
        String currentHourMinute = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        List<MessageRule> messageRuleList = messageRuleService.getActiveRules(currentHourMinute);
        List<MessageInformationDTO> messageInformationDTOS = new ArrayList<>();
        for (MessageRule messageRule : messageRuleList) {
            DateRuleEnum dateRule = messageRule.getDateRule();
            List<DocumentDTO> documentDTOList = documentService.getDocumentPlaceholderFromDocType(
                    messageRule.getDocType(),
                    messageRule.getDocStatus()
            );
            List<UUID> refIds = new ArrayList<>();
            for (Inbox inbox : messageRule.getMessageTemplate().getAccount().getInboxList()) {
                refIds.addAll(inbox.getInboxLinkList().stream().map(InboxLink::getRefId).toList());
            }
            List<Attach> attachList = attachServiceImpl.findAttachByRefIds(refIds);
            List<byte[]> attachments = getFiles(attachList);


            for (DocumentDTO document : documentDTOList) {
                String body = null;
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
                        ) {
                            body = shouldSendEmail(messageRule, document);
                        }
                    } else if (Objects.equals(dateRule, DateRuleEnum.DIAS_CORRIDOS)) {
                        if (messageRule.getSelectedDay().contains(document.calendarDayDifferenceIssueDate())) {
                            body = shouldSendEmail(messageRule, document);
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
                        ) {
                            body = shouldSendEmail(messageRule, document);
                        }
                    } else if (Objects.equals(dateRule, DateRuleEnum.DIAS_CORRIDOS)) {
                        if (messageRule.getSelectedDay().contains(document.calendarDayDifferenceIssueDate())) {
                            body = shouldSendEmail(messageRule, document);
                        }
                    }
                }

                switch (messageRule.getMessageTemplate().getAccount().getProvider()) {
                    case SMTP -> messageInformationDTOS.add(
                            EmailMessageInformationMapper.toSMTPEmailMessageInformationDTO(
                                    document,
                                    messageRule,
                                    body,
                                    attachments
                            )
                    );
                    case GMAIL, OUTLOOK -> messageInformationDTOS.add(
                            EmailMessageInformationMapper.toOAuthEmailMessageInformationDTO(
                                    document,
                                    messageRule,
                                    body,
                                    attachments
                            )
                    );
                }
            }
        }
        send(messageInformationDTOS);
        System.out.println("teste");
    }

    private String shouldSendEmail(MessageRule messageRule, DocumentDTO document) {
        return messageTemplateService.formatBodyMessage(document, messageRule.getMessageTemplate().getBody());
    }

    private List<byte[]> getFiles(List<Attach> attaches) {
        return new ArrayList<>();
    }

    private void send(
            List<MessageInformationDTO> messageInformationDTOS
    ) {
        try {
            for (MessageInformationDTO messageInformationDTO : messageInformationDTOS) {
                for (Sender<MessageInformationDTO> sender : senderList) {
                    sender.send(messageInformationDTO);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

