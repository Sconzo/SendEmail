package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
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
    public DataListWrapper<MessageTemplateDTO> listMessageTemplates(
            @RequestHeader String authorization
    ) {
        DataListWrapper<MessageTemplateDTO> dataListWrapper = messageTemplateService.listMessageTemplates();
        dataListWrapper.setMessage("Listed successfully.");
        dataListWrapper.setStatus(HTTPResponse.SC_OK);
        return dataListWrapper;
    }

    @GetMapping("/{uuid}")
    public DataWrapper<MessageTemplateDTO> getMessageTemplate(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageTemplateDTO> dataWrapper = messageTemplateService.getMessageTemplate(uuid);
        dataWrapper.setMessage("Found successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/{uuid}")
    public DataWrapper<MessageTemplateDTO> updateMessageTemplate(
            @RequestHeader String authorization,
            @RequestBody MessageTemplateRequest request,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageTemplateDTO> dataWrapper = messageTemplateService.updateMessageTemplate(uuid, request);
        dataWrapper.setMessage("Updated successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }
}
