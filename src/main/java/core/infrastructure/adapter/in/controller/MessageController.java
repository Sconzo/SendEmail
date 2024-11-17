package core.infrastructure.adapter.in.controller;

import core.domain.dto.account.AssociateAccountRequest;
import core.domain.dto.message.CreateMessageRequest;
import core.domain.service.AccountService;
import core.domain.service.MessageService;
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
