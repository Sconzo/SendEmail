package com.ti9.send.email.core.application.mapper;

import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleRequest;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;

public class MessageRuleMapper {

    public static MessageRule toEntity(MessageRuleRequest messageRuleRequest) {

       MessageRule messageRule = new MessageRule();
       messageRule.setName(messageRuleRequest.name());
       messageRule.setDateRule(messageRuleRequest.dateRule());
       messageRule.setDateIndex(messageRuleRequest.dateBase());
       messageRule.setSelectedTime(messageRuleRequest.sendingTimeList());
       messageRule.setSelectedDay(messageRuleRequest.sendingDayList());
       messageRule.setDocType(messageRuleRequest.docTypeList());
       messageRule.setDocStatus(messageRuleRequest.paymentStatusList());
       messageRule.setStatus(messageRuleRequest.status());
       messageRule.setIncludeAttachment(messageRuleRequest.sendAttachments());

       return messageRule;
    }

    public static MessageRuleDTO toDTO(MessageRule messageRule) {

       return new MessageRuleDTO(
               messageRule.getId(),
               messageRule.getName(),
               messageRule.getDateRule(),
               messageRule.getDateIndex(),
               messageRule.getSelectedTime(),
               messageRule.getSelectedDay(),
               messageRule.getDocType(),
               messageRule.getDocStatus(),
               messageRule.getStatus(),
               messageRule.getIncludeAttachment()
       );
    }

    public static SummaryMessageDTO toSummary(MessageRule messageRule) {
       return new SummaryMessageDTO(
               messageRule.getId(),
               messageRule.getName(),
               messageRule.getStatus()
       );
    }

}
