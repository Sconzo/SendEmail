package com.ti9.send.email.core.domain.service.inbox;

import com.ti9.send.email.core.application.port.out.inbox.InboxRepository;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InboxServiceImpl implements InboxService {

    private final InboxRepository repository;

    public InboxServiceImpl(InboxRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Inbox> listInboxByAccountIds(List<UUID> accountIds) {
        return repository.listByAccountIds(accountIds);
    }
}
