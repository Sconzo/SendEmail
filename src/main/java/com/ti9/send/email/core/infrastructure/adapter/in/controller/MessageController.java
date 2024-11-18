package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.ti9.send.email.core.domain.dto.message.CreateMessageRequest;
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
    public void createMessage(
            @RequestHeader String authorization,
            @RequestBody CreateMessageRequest request
    ) {
        messageRuleService.createMessage(request);
    }

    @GetMapping("/list")
    public void listMessages(
            @RequestHeader String authorization
    ) {
        messageRuleService.listMessages();
    }

    @GetMapping("{uuid}")
    public void getMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageRuleService.getMessage(uuid);
    }

    @PatchMapping("/{uuid}")
    public void updateMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageRuleService.updateMessage(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageRuleService.deleteMessage(uuid);
    }
}
