package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateDTO;
import com.ti9.send.email.core.domain.dto.message.template.MessageTemplateRequest;
import com.ti9.send.email.core.domain.service.message.template.MessageTemplateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/message-template")
@AllArgsConstructor
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    @PostMapping()
    public DataWrapper<MessageTemplateDTO> createMessageTemplate(
            @RequestHeader String authorization,
            @RequestBody @Valid MessageTemplateRequest request
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

    @GetMapping("/by-rule-id")
    public DataWrapper<MessageTemplateDTO> getMessageTemplateByRuleId(
            @RequestHeader String authorization,
            @RequestParam UUID ruleId
    ) {
        DataWrapper<MessageTemplateDTO> dataWrapper = messageTemplateService.getMessageTemplateByRuleId(ruleId);
        if(Objects.nonNull(dataWrapper.getData())) {
            dataWrapper.setMessage("Found successfully.");
        } else {
            dataWrapper.setMessage("Should create a new template.");
        }
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }

    @PatchMapping("/{uuid}")
    public DataWrapper<MessageTemplateDTO> updateMessageTemplate(
            @RequestHeader String authorization,
            @RequestBody @Valid MessageTemplateRequest request,
            @PathVariable UUID uuid
    ) {
        DataWrapper<MessageTemplateDTO> dataWrapper = messageTemplateService.updateMessageTemplate(uuid, request);
        dataWrapper.setMessage("Updated successfully.");
        dataWrapper.setStatus(HTTPResponse.SC_OK);
        return dataWrapper;
    }
}
