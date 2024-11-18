package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.ti9.send.email.core.domain.dto.message.template.CreateMessageTemplateRequest;
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
    public void createMessageTemplate(
            @RequestHeader String authorization,
            @RequestBody CreateMessageTemplateRequest request
    ) {
        messageTemplateService.createMessageTemplate(request);
    }

    @GetMapping("/list")
    public void listMessageTemplates(
            @RequestHeader String authorization
    ) {
        messageTemplateService.listMessageTemplates();
    }

    @GetMapping("{uuid}")
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
