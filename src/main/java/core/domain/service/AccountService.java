package core.domain.service;

import core.domain.dto.account.AssociateAccountRequest;

import java.util.UUID;

public interface AccountService {

    void associateAccount(AssociateAccountRequest request);

    void listAccounts();

    void getAccount(UUID id);

    void updateAccount(UUID id);

    void deleteAccount(UUID id);
}
