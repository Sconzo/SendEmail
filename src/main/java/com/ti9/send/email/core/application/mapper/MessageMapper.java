package com.ti9.send.email.core.application.mapper;

import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.dto.message.MessageDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.model.message.MessageRule;

public class MessageMapper {

    public static MessageRule toEntity(MessageRequest messageRequest) {

       MessageRule messageRule = new MessageRule();
       messageRule.setName(messageRequest.name());
       messageRule.setDateRule(messageRequest.dateRule());
       messageRule.setDateIndex(messageRequest.dateBase());
       messageRule.setSelectedTime(messageRequest.sendingTimeList());
       messageRule.setSelectedDay(messageRequest.sendingDayList());
       messageRule.setDocType(messageRequest.docTypeList());
       messageRule.setDocStatus(messageRequest.paymentStatusList());
       messageRule.setStatus(messageRequest.status());
       messageRule.setIncludeAttachment(messageRequest.sendAttachments());

       return messageRule;
    }

    public static MessageDTO toDTO(MessageRule messageRule) {

       return new MessageDTO(
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
