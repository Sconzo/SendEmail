package com.ti9.send.email.core.domain.service.account;

import com.ti9.send.email.core.domain.dto.account.AssociateAccountRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AccountService {

    void associateAccount(AssociateAccountRequest request, MultipartFile file);

    void listAccounts();

    void getAccount(UUID id);

    void updateAccount(UUID id);

    void deleteAccount(UUID id);
}
