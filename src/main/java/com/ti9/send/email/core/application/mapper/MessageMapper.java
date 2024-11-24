package com.ti9.send.email.core.application.mapper;

import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.dto.message.MessageDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.model.message.Message;

public class MessageMapper {

    public static Message toEntity(MessageRequest messageRequest) {

       Message message = new Message();
       message.setName(messageRequest.name());
       message.setDateRule(messageRequest.dateRule());
       message.setDateIndex(messageRequest.dateBase());
       message.setSelectedTime(messageRequest.sendingTimeList());
       message.setSelectedDay(messageRequest.sendingDayList());
       message.setDocType(messageRequest.docTypeList());
       message.setDocStatus(messageRequest.paymentStatusList());
       message.setStatus(messageRequest.status());
       message.setIncludeAttachment(messageRequest.sendAttachments());

       return message;
    }

    public static MessageDTO toResponse(Message message) {

       return new MessageDTO(
               message.getId(),
               message.getName(),
               message.getDateRule(),
               message.getDateIndex(),
               message.getSelectedTime(),
               message.getSelectedDay(),
               message.getDocType(),
               message.getDocStatus(),
               message.getStatus(),
               message.getIncludeAttachment()
       );
    }

    public static SummaryMessageDTO toSummary(Message message) {
       return new SummaryMessageDTO(
               message.getId(),
               message.getName(),
               message.getStatus()
       );
    }

}
