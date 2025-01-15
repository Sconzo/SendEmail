package com.ti9.send.email.core.domain.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ti9.send.email.core.domain.dto.DataListWrapper;
import com.ti9.send.email.core.domain.dto.DataWrapper;
import com.ti9.send.email.core.domain.dto.account.AccountRequest;
import com.ti9.send.email.core.domain.dto.account.AccountResponse;
import com.ti9.send.email.core.domain.dto.account.AssociateAccountRequest;
import com.ti9.send.email.core.domain.model.account.Account;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AccountService {

    void associateAccount(AssociateAccountRequest request, MultipartFile file);

    DataListWrapper<AccountResponse> listAccounts();

    DataWrapper<AccountResponse> getAccount(UUID id);

    DataWrapper<AccountResponse> updateAccount(UUID id, AccountRequest body);

    void deleteAccount(UUID id);

    DataWrapper<AccountResponse> createAccount(AccountRequest body);

    void changeStatus(UUID uuid);

    Account updateAccountSettings(UUID id, String accountSettings) throws JsonProcessingException;
}
