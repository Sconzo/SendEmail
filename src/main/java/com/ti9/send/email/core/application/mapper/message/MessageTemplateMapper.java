package com.ti9.send.email.core.application.mapper.message;

import com.ti9.send.email.core.domain.dto.BaseDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;

import java.util.Collections;

public class MessageTemplateMapper {

    public static MessageTemplate toEntity(MessageTemplateRequest request) {

        MessageTemplate messageTemplate = new MessageTemplate();

        Account senderAccount = new Account();
        senderAccount.setId(request.senderId());

        MessageRule messageRule = new MessageRule();
        messageRule.setId(request.messageRuleId());
        messageRule.setMessageTemplate(messageTemplate);

        messageTemplate.setAction(request.action());
        messageTemplate.setAccount(senderAccount);
        messageTemplate.setRecipientType(request.recipientType());
        messageTemplate.setReplyTo(request.replyTO());
        messageTemplate.setCc(request.cc());
        messageTemplate.setBcc(request.cco());
        messageTemplate.setSubject(request.subject());
        messageTemplate.setBody(request.body());
        messageTemplate.setMessageRuleList(Collections.singletonList(messageRule));

        return messageTemplate;
    }

    public static MessageTemplateDTO toDTO(MessageTemplate entity) {

        return new MessageTemplateDTO(
                entity.getId(),
                entity.getAction().getDescription(),
                new BaseDTO(entity.getAccount().getId(), entity.getAccount().getName()),
                entity.getRecipientType().getDescription(),
                entity.getReplyTo(),
                entity.getCc(),
                entity.getBcc(),
                entity.getSubject(),
                entity.getBody()
        );
    }
}
