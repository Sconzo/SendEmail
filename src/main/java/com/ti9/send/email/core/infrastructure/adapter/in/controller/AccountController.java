package com.ti9.send.email.core.infrastructure.adapter.in.controller;

import com.ti9.send.email.core.domain.dto.account.AssociateAccountRequest;
import com.ti9.send.email.core.domain.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public void associateAccount(
            @RequestHeader String authorization,
            @RequestBody AssociateAccountRequest request
    ) {
        accountService.associateAccount(request);
    }

    @GetMapping("/list")
    public void listAccounts(
            @RequestHeader String authorization
    ) {
        accountService.listAccounts();
    }

    @GetMapping("{uuid}")
    public void getAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.getAccount(uuid);
    }

    @PatchMapping("/{uuid}")
    public void updateAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.updateAccount(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteAccount(
            @RequestHeader String authorization,
            @PathVariable UUID uuid
    ) {
        accountService.deleteAccount(uuid);
    }
}
