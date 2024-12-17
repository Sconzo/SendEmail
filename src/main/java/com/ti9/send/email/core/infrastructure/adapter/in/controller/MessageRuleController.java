package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleRequest;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleDTO;
import com.ti9.send.email.core.domain.dto.message.SummaryMessageDTO;
import com.ti9.send.email.core.domain.service.message.rule.MessageRuleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message-rule")
@AllArgsConstructor
public class MessageRuleController {

    private final MessageRuleService messageRuleService;

    @PostMapping()
    public DataWrapper<MessageRuleDTO> createMessage(
            @RequestHeader String authorization,
            @RequestBody @Valid MessageRuleRequest request
    ) {
        DataWrapper<MessageRuleDTO> dataWrapper = messageRuleService.createMessage(request);
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
    public DataWrapper<MessageRuleDTO> getMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageRuleDTO> dataWrapper = messageRuleService.getMessage(uuid);
        dataWrapper.setMessage("Success.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/{uuid}")
    public DataWrapper<MessageRuleDTO> updateMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid,
            @RequestBody @Valid MessageRuleRequest request
    ) {
        DataWrapper<MessageRuleDTO> dataWrapper = messageRuleService.updateMessage(uuid, request);
        dataWrapper.setMessage("Updated successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @DeleteMapping("/{uuid}")
    public DataWrapper<MessageRuleDTO> deleteMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageRuleDTO> dataWrapper = new DataWrapper<>();
        messageRuleService.deleteMessage(uuid);
        dataWrapper.setMessage("Successfully deleted.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;

    }

    @PatchMapping("/status/{uuid}")
    public DataWrapper<MessageRuleDTO> changeStatus(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageRuleDTO> dataWrapper = new DataWrapper<>();
        messageRuleService.changeStatus(uuid);
        dataWrapper.setMessage("Updated successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }
}
