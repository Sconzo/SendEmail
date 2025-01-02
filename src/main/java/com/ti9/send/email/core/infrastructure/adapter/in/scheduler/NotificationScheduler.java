package com.ti9.send.email.core.infrastructure.adapter.in.scheduler;

import com.ti9.send.email.core.application.mapper.message.EmailMessageInformationMapper;
import com.ti9.send.email.core.domain.dto.message.information.TokenDTO;
import com.ti9.send.email.core.domain.dto.message.information.UserInformationDTO;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.dto.message.information.EmailMessageInformationDTO;
import com.ti9.send.email.core.domain.dto.message.information.MessageInformationDTO;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.model.attach.Attach;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import com.ti9.send.email.core.domain.model.inbox.InboxLink;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.enums.BaseDateEnum;
import com.ti9.send.email.core.domain.model.message.enums.DateRuleEnum;
import com.ti9.send.email.core.domain.service.attach.AttachLinkLinkServiceImpl;
import com.ti9.send.email.core.domain.service.document.DocumentService;
import com.ti9.send.email.core.domain.service.inbox.InboxService;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import com.ti9.send.email.core.domain.service.message.template.MessageTemplateService;
import com.ti9.send.email.core.domain.service.token.TokenService;
import com.ti9.send.email.core.infrastructure.adapter.out.sender.SenderFacade;
import com.ti9.send.email.core.infrastructure.adapter.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NotificationScheduler {

    private final MessageRuleService messageRuleService;
    private final MessageTemplateService messageTemplateService;
    private final DocumentService documentService;
    private final AttachLinkLinkServiceImpl attachLinkServiceImpl;
    private final SenderFacade senderFacade;
    private final InboxService inboxService;
    private final Map<ProviderEnum, TokenService> tokenServiceMap;

    @Autowired
    public NotificationScheduler(
            MessageRuleService messageRuleService,
            MessageTemplateService messageTemplateService,
            DocumentService documentService,
            AttachLinkLinkServiceImpl attachLinkServiceImpl,
            SenderFacade senderFacade,
            InboxService inboxService,
            @Qualifier("gmailTokenService") TokenService gmailTokenService,
            @Qualifier("outlookTokenService") TokenService outlookTokenService
    ) {
        this.messageRuleService = messageRuleService;
        this.messageTemplateService = messageTemplateService;
        this.documentService = documentService;
        this.attachLinkServiceImpl = attachLinkServiceImpl;
        this.senderFacade = senderFacade;
        this.inboxService = inboxService;
        this.tokenServiceMap = Map.of(
                ProviderEnum.GMAIL, gmailTokenService,
                ProviderEnum.OUTLOOK, outlookTokenService
        );
    }

    @Scheduled(cron = "${process.messages.cron.expression}")
    public void processMessages() {
        try {
            System.out.println("Tarefa executada: " + System.currentTimeMillis());
            LocalTime currentTime = LocalTime.now();
            String currentHourMinute = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            List<MessageInformationDTO> messageInformationDTOS = new ArrayList<>();
            Set<String> docTypeSet = new HashSet<>();
            Set<PaymentStatusEnum> paymentStatusEnumSet = new HashSet<>();
            Map<UUID, List<Attach>> attachMap;
            Map<UUID, List<File>> refIdsAndFileMap;

            List<MessageRule> messageRuleList = messageRuleService.getActiveRules(currentHourMinute);

            List<Inbox> inboxList = inboxService.listInboxByAccountIds(
                    messageRuleList.stream().map(messageRule ->
                            messageRule.getMessageTemplate().getAccount().getId()
                    ).toList()
            );


            for (MessageRule messageRule : messageRuleList) {
                docTypeSet.addAll(messageRule.getDocType());
                paymentStatusEnumSet.addAll(messageRule.getDocStatus());
            }

            attachMap = attachLinkServiceImpl.findAttachLinkByRefIds(
                    inboxList.stream().flatMap(inbox ->
                            inbox.getInboxLinkList().stream().map(InboxLink::getRefId)
                    ).collect(Collectors.toSet()).stream().toList()
            );
            refIdsAndFileMap = this.getFiles(attachMap);
            List<DocumentDTO> documentDTOList = documentService.getDocumentPlaceholderFromDocType(
                    docTypeSet.stream().toList(),
                    paymentStatusEnumSet.stream().toList()
            );

            for (MessageRule messageRule : messageRuleList) {
                boolean shouldBeSent = false;
                DateRuleEnum dateRule = messageRule.getDateRule();
                List<UUID> refIds = inboxList.stream().filter(inbox ->
                        inbox.getAccount().getId().equals(messageRule.getMessageTemplate().getAccount().getId())
                ).flatMap(inbox ->
                        inbox.getInboxLinkList().stream().map(InboxLink::getRefId
                        )
                ).collect(Collectors.toSet()).stream().toList();
                List<File> attachmentList = refIdsAndFileMap.entrySet().stream().filter(entry ->
                        refIds.contains(entry.getKey())
                ).flatMap(entry ->
                        entry.getValue().stream()
                ).toList();
                for (DocumentDTO document : documentDTOList) {
                    BaseDateEnum dateIndex = messageRule.getDateIndex();
                    LocalDate comparisonDate = switch (dateIndex) {
                        case EMISSAO -> document.issueDate();
                        case VENCIMENTO -> document.dueDate();
                    };
                    if (Objects.equals(dateRule, DateRuleEnum.DIAS_UTEIS) &&
                            DateUtils.isWorkingDay(LocalDate.now(), DateUtils.getBrazilianHolidays())
                    ) {
                        int workDaysDifference = DateUtils.calculateWorkDaysDifference(
                                LocalDate.now(), comparisonDate, DateUtils.getBrazilianHolidays());
                        shouldBeSent = messageRule.getSelectedDay().stream()
                                .map(Short::intValue)
                                .anyMatch(day -> day == workDaysDifference);
                    } else if (Objects.equals(dateRule, DateRuleEnum.DIAS_CORRIDOS)) {
                        int calendarDayDifference = document.calendarDayDifferenceIssueDate();
                        shouldBeSent = messageRule.getSelectedDay().contains((short) calendarDayDifference);
                    }
                    if (shouldBeSent) {
                        defineMessageTypeAndPopulateMessageInformationDTO(
                                messageRule,
                                document,
                                messageInformationDTOS,
                                messageBodyBuilder(messageRule, document),
                                attachmentList
                        );
                    }
                }
            }
            senderFacade.send(messageInformationDTOS);
            System.out.println("teste");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void defineMessageTypeAndPopulateMessageInformationDTO(
            MessageRule messageRule,
            DocumentDTO document,
            List<MessageInformationDTO> messageInformationDTOS,
            String body,
            List<File> attachmentList
    ) {
        TokenDTO tokenDTO = TokenDTO.fromJson(messageRule.getMessageTemplate().getAccount().getSettings());
        UserInformationDTO userInformationDTO = tokenServiceMap.get(
                messageRule.getMessageTemplate().getAccount().getProvider()
        ).getDecodedToken(tokenDTO);
        EmailMessageInformationDTO emailMessageInformationDTO =
                EmailMessageInformationMapper.emailMessageInformationDTO(
                        document,
                        messageRule,
                        body,
                        attachmentList,
                        userInformationDTO
                );
        switch (messageRule.getMessageTemplate().getAccount().getProvider()) {
            case SMTP -> messageInformationDTOS.add(
                    EmailMessageInformationMapper.toSMTPEmailMessageInformationDTO(
                            emailMessageInformationDTO
                    )
            );
            case GMAIL, OUTLOOK -> messageInformationDTOS.add(
                    EmailMessageInformationMapper.toOAuthEmailMessageInformationDTO(
                            emailMessageInformationDTO,
                            tokenDTO
                    )
            );
        }
        System.out.println("testa");
    }

    private String messageBodyBuilder(MessageRule messageRule, DocumentDTO document) {
        return messageTemplateService.formatBodyMessage(document, messageRule.getMessageTemplate().getBody());
    }

    private Map<UUID, List<File>> getFiles(Map<UUID, List<Attach>> attachMap) {
        Map<UUID, List<File>> refIdAndFileBytesMap = new HashMap<>();
        attachMap.forEach((uuid, attachList) -> {
            List<File> fileBytesList = new ArrayList<>();
            attachList.parallelStream().forEach(attach -> {
                    Path filePath = Path.of(attach.getFolderName(), attach.getFilename());
                    File file = filePath.toFile();
                    fileBytesList.add(file);

            });
            refIdAndFileBytesMap.put(uuid, fileBytesList);

        });
        return refIdAndFileBytesMap;
    }
}

