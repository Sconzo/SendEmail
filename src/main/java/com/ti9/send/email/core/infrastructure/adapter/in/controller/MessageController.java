package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.ti9.send.email.core.domain.dto.message.CreateMessageRequest;
import com.ti9.send.email.core.domain.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public void createMessage(
            @RequestHeader String authorization,
            @RequestBody CreateMessageRequest request
    ) {
        messageService.createMessage(request);
    }

    @GetMapping("/list")
    public void listMessages(
            @RequestHeader String authorization
    ) {
        messageService.listMessages();
    }

    @GetMapping("{uuid}")
    public void getMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageService.getMessage(uuid);
    }

    @PatchMapping("/{uuid}")
    public void updateMessage(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageService.updateMessage(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        messageService.deleteMessage(uuid);
    }
}
