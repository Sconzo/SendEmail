package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.MessageRequest;
import com.ti9.send.email.core.domain.dto.message.MessageDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private final MessageRuleService messageRuleService;

    @PostMapping()
    public DataWrapper<MessageDTO> createMessage(
            @RequestHeader String authorization,
            @RequestBody MessageRequest request
    ) {
        DataWrapper<MessageDTO> dataWrapper = messageRuleService.createMessage(request);
        dataWrapper.setMessage("Created successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @GetMapping("/list")
    public DataListWrapper<SummaryMessageDTO> listMessages(
            @RequestHeader String authorization
    ) {
        DataListWrapper<SummaryMessageDTO> dataListWrapper = messageRuleService.listMessages();
        dataListWrapper.setMessage("Success.");
        dataListWrapper.setStatus(HTTPResponse.SC_OK);
        return dataListWrapper;
    }

    @GetMapping("{uuid}")
    public DataWrapper<MessageDTO> getMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageDTO> dataWrapper = messageRuleService.getMessage(uuid);
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/{uuid}")
    public DataWrapper<MessageDTO> updateMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid,
            @RequestBody MessageRequest request
    ) {
        DataWrapper<MessageDTO> dataWrapper = messageRuleService.updateMessage(uuid, request);
        dataWrapper.setMessage("Updated successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @DeleteMapping("/{uuid}")
    public DataWrapper<MessageDTO> deleteMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageDTO> dataWrapper = new DataWrapper<>();
        messageRuleService.deleteMessage(uuid);
        dataWrapper.setMessage("Successfully deleted.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;

    }
}
