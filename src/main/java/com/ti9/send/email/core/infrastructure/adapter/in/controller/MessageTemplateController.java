package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.rule.MessageRuleDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.service.message.template.MessageTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message-template")
@AllArgsConstructor
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    @PostMapping()
    public DataWrapper<MessageTemplateDTO> createMessageTemplate(
            @RequestHeader String authorization,
            @RequestBody MessageTemplateRequest request
    ) {
        DataWrapper<MessageTemplateDTO> dataWrapper = messageTemplateService.createMessageTemplate(request);
        dataWrapper.setMessage("Created successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @GetMapping("/list")
    public void listMessageTemplates(
            @RequestHeader String authorization
    ) {
        messageTemplateService.listMessageTemplates();
    }

    @GetMapping("/{uuid}")
    public void getMessageTemplate(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageTemplateService.getMessageTemplate(uuid);
    }

    @PatchMapping("/{uuid}")
    public void updateMessageTemplate(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageTemplateService.updateMessageTemplate(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteMessageTemplate(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageTemplateService.deleteMessageTemplate(uuid);
    }
}
